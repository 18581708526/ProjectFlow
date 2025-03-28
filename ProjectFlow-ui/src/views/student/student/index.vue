<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学生姓名" prop="stuName">
        <el-input
          v-model="queryParams.stuName"
          placeholder="请输入学生姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学生学号" prop="stuCode">
        <el-input
          v-model="queryParams.stuCode"
          placeholder="请输入学生学号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="性别" prop="sex">
        <el-select v-model="queryParams.sex" placeholder="请选择性别" clearable>
          <el-option
            v-for="dict in dict.type.sys_user_sex"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['student:student:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['student:student:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['student:student:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['student:student:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="studentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" />
      <el-table-column label="学生姓名" align="center" prop="stuName" />
      <el-table-column label="学生学号" align="center" prop="stuCode" />
      <el-table-column label="性别" align="center" prop="sex">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.sex"/>
        </template>
      </el-table-column>
<!--      <el-table-column label="学生附件" align="center" prop="stuFile" />-->
      <el-table-column label="学生头像" align="center">
        <template slot-scope="scope">
          <!-- 使用v-if判断fileUrl是否存在，避免因为空字符串或null导致的错误 -->
          <el-image v-if="scope.row.stuFile" :src="scope.row.stuFile" :preview-src-list="[scope.row.stuFile]" style="width: 100px; height: 100px;">
            <div slot="error" class="image-slot">
              文件加载失败
            </div>
          </el-image>
          <span v-else>无图片</span>
        </template>
      </el-table-column>


      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['student:student:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['student:student:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改学生信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学生姓名" prop="stuName">
          <el-input v-model="form.stuName" placeholder="请输入学生姓名" />
        </el-form-item>
        <el-form-item label="学生学号" prop="stuCode">
          <el-input v-model="form.stuCode" placeholder="请输入学生学号" />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="form.sex" placeholder="请选择性别">
            <el-option
              v-for="dict in dict.type.sys_user_sex"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
<!--        <el-form-item label="学生附件" prop="stuFile">-->
<!--          <file-upload v-model="form.stuFile"/>-->
<!--        </el-form-item>-->
        <el-form-item label="学生头像">
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
                <img v-if="previewImage" :src="previewImage" class="avatar" alt="学生头像" style="width: 50% ; height: auto; left: auto">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                <div class="upload-trigger">
                  <el-button size="small" type="primary">选择文件</el-button>
                </div>
              </div>
            </template>
            <el-button style="margin-left: 10px;" @click="resetFile">重置</el-button>
            <div slot="tip" class="el-upload__tip">只允许上传图片文件。</div>
          </el-upload>
          <el-progress :percentage="uploadPercentage" v-if="uploading"></el-progress>
        </el-form-item>



      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listStudent, getStudent, delStudent, addStudent, updateStudent } from "@/api/student/student";
import { addFilefromMinio } from "@/api/minio/minio";
import { ElMessage } from 'element-ui';



export default {
  name: "Student",
  dicts: ['sys_user_sex'],
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
      // 学生信息表格数据
      studentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        stuName: null,
        stuCode: null,
        sex: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      //文件上传
      headers: {
        'Content-Type': 'multipart/form-data',
        'Authorization': "Bearer " + this.$store.state.user.token,
      },
      file: null,
      previewImage: '',
      xxx: '',
      uploadPercentage: 0,  // 上传进度
      uploading: false  // 是否正在上传
    };
  },
  created() {
    this.getList();
  },
  methods: {
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

    /** 查询学生信息列表 */
    getList() {
      this.loading = true;
      listStudent(this.queryParams).then(response => {
        this.studentList = response.rows;
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
        stuName: null,
        stuCode: null,
        sex: null,
        stuFile: null
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
      this.title = "添加学生信息";
      this.resetFile();
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getStudent(id).then(response => {
        this.form = response.data;
        this.previewImage = response.data.stuFile;
        this.open = true;
        this.title = "修改学生信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStudent(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStudent(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除学生信息编号为"' + ids + '"的数据项？').then(function() {
        return delStudent(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('student/student/export', {
        ...this.queryParams
      }, `student_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

