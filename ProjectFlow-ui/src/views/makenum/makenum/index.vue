<template>
  <div class="app-container">
    <el-upload
      ref="upload"
      :action="xxx"
      :http-request="handleUploadRequest"
      :headers="headers"
      :on-success="handleUploadSuccess"
      :on-progress="handleProgress"
      :before-upload="beforeUpload"
      :limit="1"
      accept=""
    >
      <template>
        <div class="upload-container">
          <img v-if="previewImage" :src="previewImage" class="avatar" alt="execl文件" style="width: 50% ; height: auto; left: auto">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          <div class="upload-trigger">
            <el-button size="small" type="primary">选择文件</el-button>
          </div>
        </div>
      </template>
      <el-button style="margin-left: 10px;" @click="resetFile">重置</el-button>
      <div slot="tip" class="el-upload__tip">只允许上传execl文件。</div>
    </el-upload>

  </div>
</template>

<script>
import { listMakenum, getMakenum, addMakenum, updateMakenum } from "@/api/makenum/makenum";
import {addFilefromMinio} from "@/api/minio/minio";

export default {
  name: "Makenum",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 凑数功能表格数据
      makenumList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        busName: null,
        sheetNumber: null,
        rowName: null,
        colName: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      action:'',
      previewImage: '',
      //文件上传
      headers: {
        'Content-Type': 'multipart/form-data',
        'Authorization': "Bearer " + this.$store.state.user.token,
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 下载处理后的文件 */
    downloadProcessedFile(url) {
      const link = document.createElement('a');
      link.href = url;
      link.download = 'processed_file.xlsx';
      document.body.appendChild(link);
      link.click();
      document.body.removeChild(link);
    },


    //文件上传接口方法
    handleUploadRequest: async function (option) {
      this.uploading=true;
      const file = option.file;

      try {
        const formData = new FormData();
        formData.append('file', file);
        // const =await addFilefromMinio(formData);

        // 使用 option.onProgress 来更新进度
        const fileUrl = await addFilefromMinio(formData, (event) => {
          option.onProgress({
            percent: Math.round((event.loaded / event.total) * 100)
          });
        });

        this.handleUploadSuccess({data: fileUrl}, file, []);

      } catch (error) {
        console.error('上传失败，错误信息:', error);
      }finally {
        this.uploading=false;
      }
    },
    handleUploadSuccess(response, file, fileList) {
      if (response && response.data) {
        this.form.stuFile = response.data;
        this.previewImage = response.data;
      } else {
        console.error('Response data is undefined');
      }
    },
    beforeUpload(file) {
      const isImage = file.type.startsWith('');/*image/*/
      if (!isImage) {
        this.$message.error('只能上传图片文件！');
        return false;
      }
      return true;
    },
    handleProgress(event, file, fileList) {
      this.uploadPercentage = event.percent;
    },
    resetFile() {
      this.$refs.upload.clearFiles();
      this.previewImage = null;  // 重置预览图片
      this.form.stuFile = '';  // 重置表单字段
      this.uploadPercentage = 0;  // 重置上传进度
      this.uploading = false;  // 重置上传状态
    },
    /** 查询凑数功能列表 */
    getList() {
      this.loading = true;
      listMakenum(this.queryParams).then(response => {
        this.makenumList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        busName: null,
        sheetNumber: null,
        rowName: null,
        colName: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加凑数功能";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMakenum(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改凑数功能";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMakenum(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMakenum(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('makenum/makenum/export', {
        ...this.queryParams
      }, `makenum_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
