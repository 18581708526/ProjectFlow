<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="70px">

      <el-form-item label="流程KEY" prop="category">
        <el-input
          v-model="queryParams.key"
          placeholder="请输入流程KEY"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="流程名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入流程名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="版本号" prop="version">
        <el-input
          v-model="queryParams.version"
          placeholder="请输入版本号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="流程状态" prop="suspensionState">
        <el-select v-model="queryParams.suspensionState" placeholder="请选择流程状态" clearable>
          <el-option
            v-for="dict in dict.type.prodef_status"
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
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['workflow:workflow:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="workflowList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
<!--      <el-table-column label="流程定义的唯一标识符" align="center" prop="id" />-->
<!--      <el-table-column label="记录的版本号，用于乐观锁机制" align="center" prop="rev" />-->
<!--      <el-table-column label="流程类别" align="center" prop="category" />-->
      <el-table-column label="流程名称" align="center" prop="name" />
      <el-table-column label="流程key" align="center" prop="key" />
      <el-table-column label="版本号" align="center" prop="version" />
<!--      <el-table-column label="部署的唯一标识符，指向 act_re_deployment 表中的记录" align="center" prop="deploymentId" />-->
      <el-table-column label="流程资源文件" align="center" prop="resourceName" />
<!--      <el-table-column label="流程定义资源的文件名，通常是 .bpmn20.xml 文件的名称" align="center" prop="dgrmResourceName" />-->
      <el-table-column label="流程描述" align="center" prop="description" />
<!--      <el-table-column label="布尔值，表示该流程定义是否有一个启动表单" align="center" prop="hasStartFormKey" />-->
<!--      <el-table-column label=" 布尔值，表示该流程定义是否包含图形表示" align="center" prop="hasGraphicalNotation" />-->
      <el-table-column label="流程状态" align="center" prop="suspensionState">
        <template slot-scope="scope">
        <dict-tag :options="dict.type.prodef_status" :value="scope.row.suspensionState"></dict-tag>
        </template>
      </el-table-column>
<!--      <el-table-column label="租户ID，用于多租户环境。" align="center" prop="tenantId" />-->
<!--      <el-table-column label="引擎版本，表示创建流程定义时使用的Flowable引擎版本" align="center" prop="engineVersion" />-->
<!--      <el-table-column label="派生自的流程定义ID，用于表示流程定义之间的继承关系" align="center" prop="derivedFrom" />-->
<!--      <el-table-column label="根流程定义ID，用于表示流程定义的根节点。" align="center" prop="derivedFromRoot" />-->
<!--      <el-table-column label="派生版本号，用于表示从某个流程定义派生出的版本号。" align="center" prop="derivedVersion" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['workflow:workflow:edit']"-->
<!--          >修改</el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
            @click="iniProcess(scope.row)"
            v-show="scope.row.suspensionState===1"
            v-hasPermi="['workflow:workflow:iniProcess']"
          >发起流程</el-button>

          <el-button
            size="mini"
            type="text"
            icon="el-icon-error"
            @click="disableProcess(scope.row)"
            v-show="scope.row.suspensionState===1"
            v-hasPermi="['workflow:workflow:iniProcess']"
          >禁用</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-success"
            @click="ableProcess(scope.row)"
            v-show="scope.row.suspensionState===2"
            v-hasPermi="['workflow:workflow:iniProcess']"
          >激活</el-button>
          <el-popover
            placement="top-start"
            title="注意"
            width="20"
            trigger="hover"
            content="当改流程定义下无在途流程时方可删除">
          <el-button
            slot="reference"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['workflow:workflow:remove']"
          >删除</el-button>
          </el-popover>
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

    <!-- 添加或修改流程定义对话框 -->
<!--    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>-->
<!--      <el-form ref="form" :model="form" :rules="rules" label-width="80px">-->
<!--        <el-form-item label="记录的版本号，用于乐观锁机制" prop="rev">-->
<!--          <el-input v-model="form.rev" placeholder="请输入记录的版本号，用于乐观锁机制" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="流程定义的类别，通常用于分类管理" prop="category">-->
<!--          <el-input v-model="form.category" placeholder="请输入流程定义的类别，通常用于分类管理" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="流程定义的名称" prop="name">-->
<!--          <el-input v-model="form.name" placeholder="请输入流程定义的名称" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="流程定义的键，用于唯一标识一个流程模型" prop="key">-->
<!--          <el-input v-model="form.key" placeholder="请输入流程定义的键，用于唯一标识一个流程模型" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="流程定义的版本号，每次部署相同 KEY_ 的新流程定义时，版本号会递增" prop="version">-->
<!--          <el-input v-model="form.version" placeholder="请输入流程定义的版本号，每次部署相同 KEY_ 的新流程定义时，版本号会递增" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="部署的唯一标识符，指向 act_re_deployment 表中的记录" prop="deploymentId">-->
<!--          <el-input v-model="form.deploymentId" placeholder="请输入部署的唯一标识符，指向 act_re_deployment 表中的记录" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="流程定义资源的文件名，通常是 .bpmn20.xml 文件的名称" prop="resourceName">-->
<!--          <el-input v-model="form.resourceName" type="textarea" placeholder="请输入内容" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="流程定义资源的文件名，通常是 .bpmn20.xml 文件的名称" prop="dgrmResourceName">-->
<!--          <el-input v-model="form.dgrmResourceName" type="textarea" placeholder="请输入内容" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="流程定义的描述信息" prop="description">-->
<!--          <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="布尔值，表示该流程定义是否有一个启动表单" prop="hasStartFormKey">-->
<!--          <el-input v-model="form.hasStartFormKey" placeholder="请输入布尔值，表示该流程定义是否有一个启动表单" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label=" 布尔值，表示该流程定义是否包含图形表示" prop="hasGraphicalNotation">-->
<!--          <el-input v-model="form.hasGraphicalNotation" placeholder="请输入 布尔值，表示该流程定义是否包含图形表示" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="指示流程定义的状态，1 表示激活状态，2 表示挂起状态。" prop="suspensionState">-->
<!--          <el-input v-model="form.suspensionState" placeholder="请输入指示流程定义的状态，1 表示激活状态，2 表示挂起状态。" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="租户ID，用于多租户环境。" prop="tenantId">-->
<!--          <el-input v-model="form.tenantId" placeholder="请输入租户ID，用于多租户环境。" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="引擎版本，表示创建流程定义时使用的Flowable引擎版本" prop="engineVersion">-->
<!--          <el-input v-model="form.engineVersion" placeholder="请输入引擎版本，表示创建流程定义时使用的Flowable引擎版本" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="派生自的流程定义ID，用于表示流程定义之间的继承关系" prop="derivedFrom">-->
<!--          <el-input v-model="form.derivedFrom" placeholder="请输入派生自的流程定义ID，用于表示流程定义之间的继承关系" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="根流程定义ID，用于表示流程定义的根节点。" prop="derivedFromRoot">-->
<!--          <el-input v-model="form.derivedFromRoot" placeholder="请输入根流程定义ID，用于表示流程定义的根节点。" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="派生版本号，用于表示从某个流程定义派生出的版本号。" prop="derivedVersion">-->
<!--          <el-input v-model="form.derivedVersion" placeholder="请输入派生版本号，用于表示从某个流程定义派生出的版本号。" />-->
<!--        </el-form-item>-->
<!--      </el-form>-->
<!--      <div slot="footer" class="dialog-footer">-->
<!--        <el-button type="primary" @click="submitForm">确 定</el-button>-->
<!--        <el-button @click="cancel">取 消</el-button>-->
<!--      </div>-->
<!--    </el-dialog>-->

    <!-- 发起流程的对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="process" :model="process" :rules="prorules" label-width="80px">
        <el-form-item label="请假学生姓名" prop="name">
          <el-input v-model="process.name" placeholder="请输入假学生姓名" />
        </el-form-item>
        <el-form-item label="请假天数" prop="day">
          <el-input v-model="process.day" placeholder="请输入请假天数" />
        </el-form-item>
        <el-form-item label="请假原因" prop="reason">
          <el-input v-model="process.reason" placeholder="请输入请假的原因" />
        </el-form-item>
        <el-form-item v-show="false" label="流程key" prop="key">
          <el-input v-model="process.key"/>
        </el-form-item>
        <el-form-item label="审批人" prop="approver">
          <UserSearch v-model="process.approver" @user-selected="onUserSelected" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="iniProcesssubmitForm(process)">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listWorkflow, getWorkflow, delWorkflow, addWorkflow, updateWorkflow,iniProcessSubmit,ableProcess,disableProcess } from "@/api/workflow/workflow";

export default {
  name: "Workflow",
  dicts: ['prodef_status'],
  data() {
    return {
      //流程表单数据对象
      process:{
        name:"", //学生姓名
        day:"", //请假天数
        reason:"",//请假原因
        key:"",
        approver:""
      },
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      users:[],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 流程定义表格数据
      workflowList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        rev: null,
        category: null,
        name: null,
        key: null,
        version: null,
        deploymentId: null,
        resourceName: null,
        dgrmResourceName: null,
        description: null,
        hasStartFormKey: null,
        hasGraphicalNotation: null,
        suspensionState: null,
        tenantId: null,
        engineVersion: null,
        derivedFrom: null,
        derivedFromRoot: null,
        derivedVersion: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        key: [
          { required: true, message: "流程定义的键，用于唯一标识一个流程模型不能为空", trigger: "blur" }
        ],
        version: [
          { required: true, message: "流程定义的版本号，每次部署相同 KEY_ 的新流程定义时，版本号会递增不能为空", trigger: "blur" }
        ],
        hasStartFormKey: [
          { required: true, message: "布尔值，表示该流程定义是否有一个启动表单不能为空", trigger: "blur" }
        ],
        derivedVersion: [
          { required: true, message: "派生版本号，用于表示从某个流程定义派生出的版本号。不能为空", trigger: "blur" }
        ]
      },
      prorules:{
        name: [
          { required: true, message: "学生姓名不能为空", trigger: "blur" }
        ],
        day: [
          { required: true, message: "请假天数不能为空", trigger: "blur" }
        ],
        reason: [
          { required: true, message: "请假原因不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    onUserSelected(user) {
      this.process.approver = user.userId;
    },
    /** 查询流程定义列表 */
    getList() {
      this.loading = true;
      listWorkflow(this.queryParams).then(response => {
        this.workflowList = response.rows;
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
        rev: null,
        category: null,
        name: null,
        key: this.form.key,
        version: null,
        deploymentId: null,
        resourceName: null,
        dgrmResourceName: null,
        description: null,
        hasStartFormKey: null,
        hasGraphicalNotation: null,
        suspensionState: null,
        tenantId: null,
        engineVersion: null,
        derivedFrom: null,
        derivedFromRoot: null,
        derivedVersion: null
      };
      this.process={
        name:"",
        day:"",
        reason:""
      }
      this.resetForm("form");
      this.resetForm("process");
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
      this.title = "添加流程定义";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getWorkflow(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改流程定义";
      });
    },
    iniProcess(row){
      this.process.key=row.key;
      this.open = true;
    },
    disableProcess(row){
      disableProcess(row.id).then(response=>{
        this.$modal.msgSuccess(response.data.msg);
        this.getList();
      })

    },
    ableProcess(row){
      ableProcess(row.id).then(response=>{
        this.$modal.msgSuccess(response.data.msg);
        this.getList();
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateWorkflow(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWorkflow(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 提交按钮 */
    iniProcesssubmitForm() {
      this.$refs["process"].validate(valid => {
        if (valid) {
            iniProcessSubmit(this.process).then(response => {
              this.$modal.msgSuccess("发起流程成功！！！");
              this.open = false;
              this.getList();
            });
         }
      });
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      const name=row.name;
      const version=row.version;
      this.$modal.confirm('是否确认删除流程定义版本号为"' + version + '",名称为"'+name+'"的流程定义吗？').then(function() {
        return delWorkflow(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('workflow/workflow/export', {
        ...this.queryParams
      }, `workflow_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
