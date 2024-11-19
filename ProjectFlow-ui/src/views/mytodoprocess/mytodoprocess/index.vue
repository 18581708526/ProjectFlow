<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="流程名称" prop="wfFwname">
        <el-input
          v-model="queryParams.wfFwname"
          placeholder="请输入流程名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="流程发起时间" prop="wfStarttime">
        <el-date-picker clearable
          v-model="queryParams.wfStarttime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择流程发起时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="发起人" prop="wfInitatorName">
        <el-input
          v-model="queryParams.wfInitatorName"
          placeholder="请输入发起人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          icon="el-icon-plus"-->
<!--          size="mini"-->
<!--          @click="handleAdd"-->
<!--          v-hasPermi="['mytodoprocess:mytodoprocess:add']"-->
<!--        >新增</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="success"-->
<!--          plain-->
<!--          icon="el-icon-edit"-->
<!--          size="mini"-->
<!--          :disabled="single"-->
<!--          @click="handleUpdate"-->
<!--          v-hasPermi="['mytodoprocess:mytodoprocess:edit']"-->
<!--        >修改</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="danger"-->
<!--          plain-->
<!--          icon="el-icon-delete"-->
<!--          size="mini"-->
<!--          :disabled="multiple"-->
<!--          @click="handleDelete"-->
<!--          v-hasPermi="['mytodoprocess:mytodoprocess:remove']"-->
<!--        >删除</el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mytodoprocess:mytodoprocess:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mytodoprocessList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="我的待办流程主键id" align="center" prop="wfFwid" />
      <el-table-column label="流程名称" align="center" prop="wfFwname" />
      <el-table-column label="流程发起时间" align="center" prop="wfStarttime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.wfStarttime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="发起人" align="center" prop="wfInitatorName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handProcess(scope.row)"
            v-hasPermi="['mytodoprocess:mytodoprocess:edit']"
          >审批</el-button>

<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['mytodoprocess:mytodoprocess:edit']"-->
<!--          >修改</el-button>-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            :disabled="true"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['mytodoprocess:mytodoprocess:remove']"-->
<!--          >删除</el-button>-->
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

    <!-- 修改成流程中的数据 我的待办对话框-->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="processVersion" :model="processVersion" label-width="80px">
        <el-form-item label="学生姓名" prop="leaveTask">
          <span>{{ processVersion.leaveTask }}</span>
        </el-form-item>
        <el-form-item label="请假天数" prop="day">
          <span>{{ processVersion.day }}</span>
        </el-form-item>
        <el-form-item label="请假原因" prop="reason">
          <span>{{ processVersion.reason }}</span>
        </el-form-item>
        <el-form-item hidden="hidden" label="taskid（隐藏字段）" prop="wfTaskid">
          <span>{{ processVersion.wfTaskid }}</span>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="success" @click="ProcessForm">通 过</el-button>
        <el-button type="danger" @click="rejectFormclick">驳 回</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>



    <el-dialog :title="rejecttitle" :visible.sync="rejectopen" width="500px" append-to-body>
      <el-form ref="processVersion" :model="processVersion" label-width="80px">
        <el-form-item label="驳回原因" prop="leaveTask">
          <editor required v-model="processVersion.rejectReason" placeholder="请输入驳回原因" :min-height="192" />
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="danger" @click="rejectSubm">确定驳回</el-button>
        <el-button @click="rejectcancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listMytodoprocess, getMytodoprocess, delMytodoprocess, Provedprocess,getProcessVaryies,Rejectprocess} from "@/api/mytodoprocess/mytodoprocess";

export default {
  name: "Mytodoprocess",
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
      // 我的待办表格数据
      mytodoprocessList: [],
      // 弹出层标题
      title: "",
      rejecttitle:"",
      // 是否显示弹出层
      open: false,
      rejectopen:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        wfFwname: null,
        wfStarttime: null,
        wfInitatorName: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      },
      processVersion:{
        day:null,
        reason:null,
        leaveTask:null,
        rejectReason:null,
        wfTaskid:null
      },
      rejectForm:{
        reason:null,
        taskId:null
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询我的待办列表 */
    getList() {
      this.loading = true;
      listMytodoprocess(this.queryParams).then(response => {
        this.mytodoprocessList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    rejectcancel(){
      this.rejectopen=false;
    },
    // 表单重置
    reset() {
      this.form = {
        wfFwid: null,
        wfFwname: null,
        wfInitatorId: null,
        wfStarttime: null,
        wfTaskid: null,
        wfBussiskey: null,
        wfInitatorName: null
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
      this.ids = selection.map(item => item.wfFwid)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加我的待办";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const wfFwid = row.wfFwid || this.ids
      getMytodoprocess(wfFwid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改我的待办";
      });
    },

    /** 打开审批界面，两个选择：通过或者驳回*/
    handProcess(row){
      getProcessVaryies(row.wfTaskid).then(response=>{
        this.processVersion=response.data;
        this.processVersion.wfTaskid=row.wfTaskid;
        this.open=true;
        this.title = "流程审批";
      })
    },


    /** 提交按钮 */
    ProcessForm() {
        Provedprocess(this.processVersion.wfTaskid).then(response => {
          this.$modal.msgSuccess("审批成功");
          this.open = false;
          this.getList();
        });
    },
    /**驳回按钮，需要输入驳回原因*/
    rejectFormclick(){
      this.rejectopen=true;
      this.rejecttitle = "请填写驳回原因";
    },
    /**确认驳回操作*/
    rejectSubm(){
       this.rejectForm.reason=this.processVersion.rejectReason;
       this.rejectForm.taskId=this.processVersion.wfTaskid;
        Rejectprocess(this.rejectForm).then(response => {
          this.$modal.msgSuccess("审批成功");
          this.rejectopen = false;
          this.open = false;
          this.getList();
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const wfFwids = row.wfFwid || this.ids;
      this.$modal.confirm('是否确认删除我的待办编号为"' + wfFwids + '"的数据项？').then(function() {
        return delMytodoprocess(wfFwids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('mytodoprocess/mytodoprocess/export', {
        ...this.queryParams
      }, `mytodoprocess_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
