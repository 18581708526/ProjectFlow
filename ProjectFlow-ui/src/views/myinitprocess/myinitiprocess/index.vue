<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="流程名称" prop="wfWfname">
        <el-input
          v-model="queryParams.wfWfname"
          placeholder="请输入流程名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发起时间" prop="wfStarttime">
        <el-date-picker clearable
          v-model="queryParams.wfStarttime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择发起时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="流程状态" prop="wfState">
        <el-select v-model="queryParams.wfState" placeholder="请选择流程状态" clearable>
          <el-option
            v-for="dict in dict.type.bus_appstate"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="审批时间" prop="wfApprtime">
        <el-date-picker clearable
          v-model="queryParams.wfApprtime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择审批时间">
        </el-date-picker>
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
          v-hasPermi="['myinitprocess:myinitiprocess:add']"
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
          v-hasPermi="['myinitprocess:myinitiprocess:edit']"
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
          v-hasPermi="['myinitprocess:myinitiprocess:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['myinitprocess:myinitiprocess:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="myinitiprocessList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="我的发起流程主键id" align="center" prop="wfWfid" />
      <el-table-column label="流程名称" align="center" prop="wfWfname" />
      <el-table-column label="发起时间" align="center" prop="wfStarttime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.wfStarttime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="流程状态" align="center" prop="wfState">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.bus_appstate" :value="scope.row.wfState"/>
        </template>
      </el-table-column>
      <el-table-column label="审批时间" align="center" prop="wfApprtime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.wfApprtime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['myinitprocess:myinitiprocess:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="viewdiagram(scope.row)"
           v-hasPermi="['myinitprocess:myinitiprocess:edit']"
          >流程跟踪</el-button>



          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            :disabled="true"
            v-hasPermi="['myinitprocess:myinitiprocess:remove']"
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

    <!-- 添加或修改我的发起对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMyinitiprocess, getMyinitiprocess, delMyinitiprocess, addMyinitiprocess, updateMyinitiprocess ,Toviewdiagram} from "@/api/myinitprocess/myinitiprocess";
import axios from "axios";
import {getToken} from "@/utils/auth";

export default {
  name: "Myinitiprocess",
  dicts: ['bus_appstate'],
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
      // 我的发起表格数据
      myinitiprocessList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        wfWfname: null,
        wfTaskid: null,
        wfStarttime: null,
        wfState: null,
        wfApprtime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        wfTaskid: [
          { required: true, message: "流程实例taskid不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询我的发起列表 */
    getList() {
      this.loading = true;
      listMyinitiprocess(this.queryParams).then(response => {
        this.myinitiprocessList = response.rows;
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
        wfWfid: null,
        wfTaskid: null,
        wfWfname: null,
        wfStarttime: null,
        wfState: null,
        wfApprtime: null
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
      this.ids = selection.map(item => item.wfWfid)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加我的发起";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const wfWfid = row.wfWfid || this.ids
      getMyinitiprocess(wfWfid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改我的发起";
      });
    },

    /**
     * 查看流程图
     */

    viewdiagram(row) {
      const taskId = row.wfTaskid;
      this.fetchProcessDiagram(taskId);
    },
    fetchProcessDiagram(taskId) {
      const url = `http://localhost/dev-api/leaveProcess/processDiagram/${taskId}`;
      const token = getToken();

      axios.get(url, {
        responseType: 'blob',
        headers: {
        Authorization: `Bearer ${token}`
      }
     })
        .then(response => {
          const blob = new Blob([response.data], { type: 'image/png' });
          const url = URL.createObjectURL(blob);
          this.showProcessDiagram(url);
        })
        .catch(error => {
          console.error('获取流程图失败:', error);
        });
    },
    showProcessDiagram(url) {
      this.$alert(`<img src="${url}" alt="流程图" class="process-diagram">`, '流程图', {
        dangerouslyUseHTMLString: true,
        showConfirmButton: false,
        showClose: true
      });
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.wfWfid != null) {
            updateMyinitiprocess(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMyinitiprocess(this.form).then(response => {
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
      const wfWfids = row.wfWfid || this.ids;
      this.$modal.confirm('是否确认删除我的发起编号为"' + wfWfids + '"的数据项？').then(function() {
        return delMyinitiprocess(wfWfids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('myinitprocess/myinitiprocess/export', {
        ...this.queryParams
      }, `myinitiprocess_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style scoped>.process-diagram {
  width: 100%;
  max-width: 600px;
  height: auto;
  display: block;
  margin: 0 auto;
}

</style>
