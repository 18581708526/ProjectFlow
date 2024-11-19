<template>
  <div class="app-container">

    <el-row :gutter="20">
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
              v-model="ProcessCategoryName"
              placeholder="请输入流程分类名称"
              clearable
              size="small"
              prefix-icon="el-icon-search"
              style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
              :data="categoryOptions"
              :props="defaultProps"
              :expand-on-click-node="false"
              :filter-node-method="filterNode"
              ref="tree"
              node-key="id"
              default-expand-all
              highlight-current
              @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                 label-width="68px">
          <el-form-item label="模型名称" prop="name">
            <el-input
                v-model="queryParams.name"
                placeholder="请输入模型名称"
                clearable
                @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="模型Key" prop="modelKey">
            <el-input
                v-model="queryParams.modelKey"
                placeholder="请输入模型唯一Key"
                clearable
                @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="分类id" prop="categoryId" v-show="false">
            <el-input
                v-model="queryParams.categoryId"
                placeholder="请输入模型唯一Key"
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
          <el-form-item label="模型类型" prop="suspensionState">
            <el-select v-model="queryParams.modelType" placeholder="请选择模型类型" clearable>
              <el-option
                  v-for="dict in dict.type.model_type"
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
                v-hasPermi="['model:model:add']"
            >新增
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
                type="success"
                plain
                icon="el-icon-edit"
                size="mini"
                :disabled="single"
                @click="handleUpdate"
                v-hasPermi="['model:model:edit']"
            >修改
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
                type="danger"
                plain
                icon="el-icon-delete"
                size="mini"
                :disabled="multiple"
                @click="handleDelete"
                v-hasPermi="['model:model:remove']"
            >删除
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
                type="warning"
                plain
                icon="el-icon-download"
                size="mini"
                @click="handleExport"
                v-hasPermi="['model:model:export']"
            >导出
            </el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="modelList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"/>
          <!--      <el-table-column label="id" align="center" prop="id" />-->
          <el-table-column label="模型名称" align="center" prop="name"/>
          <el-table-column label="模型Key" align="center" prop="modelKey"/>
          <el-table-column label="所属分类" align="center" prop="categoryName"/>
<!--          <el-table-column label="所属分类id" align="center" prop="categoryId" v-show="false"/>-->
          <el-table-column label="描述" align="center" prop="description"/>
          <el-table-column label="创建时间" align="center" prop="created" width="180">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.created, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="创建人" align="center" prop="createdBy"/>
          <el-table-column label="修改时间" align="center" prop="lastUpdated" width="180">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.lastUpdated, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="修改人" align="center" prop="lastUpdatedBy"/>
          <el-table-column label="版本号" align="center" prop="version"/>
          <el-table-column label="模型类型" align="center" prop="modelType">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.model_type" :value="scope.row.modelType"></dict-tag>
            </template>
          </el-table-column>

          <el-table-column label="操作" align="center" width="250" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <!--          <el-button-->
              <!--            size="mini"-->
              <!--            type="text"-->
              <!--            icon="el-icon-edit"-->
              <!--            @click="handleUpdate(scope.row)"-->
              <!--            v-hasPermi="['model:model:edit']"-->
              <!--          >修改</el-button>-->
              <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="designProcess(scope.row)"
                  v-hasPermi="['model:model:edit']"
              >去设计
              </el-button>
              <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="deployePro(scope.row)"
                  v-hasPermi="['model:model:edit']"
              >部署模型
              </el-button>
              <!--          <el-button-->
              <!--            size="mini"-->
              <!--            type="text"-->
              <!--            icon="el-icon-delete"-->
              <!--            @click="handleDelete(scope.row)"-->
              <!--            v-hasPermi="['model:model:remove']"-->
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
      </el-col>
    </el-row>

    <!--    TODO 这儿加上对流程的分类数据-->

    <!-- 添加或修改模型管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="模型名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入模型名称"/>
        </el-form-item>
        <el-form-item label="模型Key" prop="modelKey">
          <el-input v-model="form.modelKey" placeholder="请输入模型唯一Key"/>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!--    <el-drawer-->
    <!--      title="流程设计器"-->
    <!--      :visible.sync="drawer"-->
    <!--      size="90%">-->
    <!--      <BpmnModeler :modelId="form.id" :key="form.id" />-->
    <!--    </el-drawer>-->
  </div>

</template>

<script>
import {listModel, getModel, delModel, addModel, updateModel, deployePro} from "@/api/model/model";
import BpmnModeler from "@/components/BpmnModeler/index";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {categoryTreeSelect} from "@/api/category/category";

export default {
  name: "Model",
  dicts: ['model_type'],
  components: {BpmnModeler,Treeselect},
  data() {
    return {
      ProcessCategoryName: undefined,
      drawer: false,
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
      // 模型管理表格数据
      modelList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        modelKey: null,
        modelComment: null,
        version: null,
        modelType: null,
        categoryId:null
      },
      ModelProVo: {
        modelId: null,
        key: null,
        name: null
      },
      defaultProps: {
        children: "children",
        label: "label"
      },
      categoryOptions:undefined,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          {required: true, message: "模型名称不能为空", trigger: "blur"}
        ],
        modelKey: [
          {required: true, message: "模型唯一Key不能为空", trigger: "blur"}
        ],
      }
    };
  },
  watch: {
    // 根据名称筛选
    ProcessCategoryName(val) {
      this.$refs.tree.filter(val);
    }
  },

  created() {
    this.getList();
    this.getCategoryTree();
  },
  methods: {
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.categoryId = data.id;
      this.handleQuery();
    },
    getCategoryTree(){
        categoryTreeSelect().then(response => {
            this.categoryOptions = response.data;
        });
    },
    // 筛选节点
    filterNode(value, data) {

      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    designProcess(row) {
      // this.form.id = row.id;
      this.$router.push({name: 'BpmnModeler', params: {modelId: row.id}})
      // this.form.id = row.id;
      // this.drawer=true;
      // designProcess().then(response => {
      //   window.open('http://'+process.env.VUE_APP_FLOWABLE_DNS+':'+process.env.VUE_APP_FLOWABLEUI_HOST+'/modeler/#/editor/'+modeid);
      // });
    },

    deployePro(row) {
      // this.ModelProVo.modelId=row.id;
      // this.ModelProVo.key=row.modelKey;
      // this.ModelProVo.name=row.name;
      deployePro(row.id).then(response => {
        this.$modal.msgSuccess(response.data);
      })
    },

    /** 查询模型管理列表 */
    getList() {
      this.loading = true;
      listModel(this.queryParams).then(response => {
        this.modelList = response.rows;
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
        name: null,
        modelKey: null,
        description: null,
        modelComment: null,
        created: null,
        createdBy: null,
        lastUpdated: null,
        lastUpdatedBy: null,
        version: null,
        modelEditorJson: null,
        thumbnail: null,
        modelType: null,
        tenantId: null,
        categoryId:null,
        categoryName:null
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加模型管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getModel(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改模型管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateModel(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addModel(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除模型管理编号为"' + ids + '"的数据项？').then(function () {
        return delModel(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('model/model/export', {
        ...this.queryParams
      }, `model_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
