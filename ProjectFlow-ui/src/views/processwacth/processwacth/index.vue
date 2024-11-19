<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="流程名称" prop="wfWfname">
        <el-input v-model="queryParams.wfWfname" placeholder="请输入流程名称" clearable
                  @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="发起时间" prop="wfStarttime">
        <el-date-picker clearable v-model="queryParams.wfStarttime" type="date" value-format="yyyy-MM-dd"
                        placeholder="请选择发起时间"></el-date-picker>
      </el-form-item>
      <el-form-item label="流程状态" prop="wfState">
        <el-select v-model="queryParams.wfState" placeholder="请选择流程状态" clearable>
          <el-option v-for="dict in dict.type.bus_appstate" :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="审批时间" prop="wfApprtime">
        <el-date-picker clearable v-model="queryParams.wfApprtime" type="date" value-format="yyyy-MM-dd"
                        placeholder="请选择审批时间"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="processwacthList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="我的发起流程数据表主键id" align="center" prop="wfWfid"/>
      <el-table-column label="流程名称" align="center" prop="wfWfname"/>
      <el-table-column label="发起时间" align="center" prop="wfStarttime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.wfStarttime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="流程状态" align="center" prop="wfState">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.bus_appstate" :value="scope.row.wfState"/>
        </template>
      </el-table-column>
      <el-table-column label="审批时间" align="center" prop="wfApprtime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.wfApprtime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['mytodoprocess:mytodoprocess:query']">修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mytodoprocess:mytodoprocess:query']"
          >删除
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-more"
            @click="lookMessage(scope.row)"
            v-hasPermi="['mytodoprocess:mytodoprocess:query']">
            查看详情
          </el-button>
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
    <!-- 添加或修改流程监控对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 流程的核心信息： 包括表单数据，流转信息，流程跟踪数据   -->
    <el-drawer
      title="我是标题"
      :visible.sync="drawer"
      size="60%">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="表单数据" name="first">
          {{ form.wfTaskid }}
          <span>表单数据</span>
        </el-tab-pane>
        <!-- 流转信息显示 -->




<!--        <el-tab-pane label="流转信息" name="second">-->
<!--          <el-timeline>-->
<!--            <el-timeline-item-->
<!--              v-for="(item, index) in flowInstanceVOList"-->
<!--              :key="index"-->
<!--              :icon="item.icon"-->
<!--              :type="item.type"-->
<!--              :color="item.color"-->
<!--              :timestamp="item.taskName"-->
<!--              :size="item.large"-->
<!--              placement="top"-->
<!--            >-->
<!--              <el-card>-->
<!--                <div class="timeline-content">-->
<!--                  <p>{{ item.assignee }} <strong>提交于</strong>{{ item.startTime }}</p>-->
<!--                  <p v-if="item.endTime"><strong>结束于</strong> {{ item.endTime }}</p>-->
<!--                  <p v-if="item.duration"><strong>耗时</strong> {{ item.duration }}</p>-->
<!--                </div>-->
<!--                <el-divider content-position="left">分割线</el-divider>-->
<!--              </el-card>-->
<!--            </el-timeline-item>-->
<!--          </el-timeline>-->
<!--        </el-tab-pane>-->
        <!--网上抄的流转信息格式-->

        <el-tab-pane label="流转记录" name="second">
          <el-card class="box-card" shadow="never">
            <el-col :span="20" :offset="2">
              <div class="block">
                <el-timeline>
                  <el-timeline-item v-for="(item,index) in flowInstanceVOList" :key="index"
                                    :icon="setIcon(item.endTime)" :color="setColor(item.endTime)">
                    <p style="font-weight: 700">{{ item.taskName }}</p>
                    <el-card v-if="item.activityType === 'startEvent'" class="box-card" shadow="hover">
                      {{ item.assignee }} 在 {{ item.startTime }} 发起流程
                    </el-card>
                    <el-card v-if="item.activityType === 'userTask'" class="box-card" shadow="hover">
                      <el-descriptions :column="5" :labelStyle="{'font-weight': 'bold'}">
                        <el-descriptions-item label="实际审批人">{{ item.assignee || '-' }}</el-descriptions-item>
                        <el-descriptions-item label="候选审批人">{{ item.candidate || '-' }}</el-descriptions-item>
                        <el-descriptions-item label="接收时间">{{ item.startTime || '-' }}</el-descriptions-item>
                        <el-descriptions-item label="处理时间">{{ item.endTime || '-' }}</el-descriptions-item>
                        <el-descriptions-item label="耗时">{{ item.duration || '-' }}</el-descriptions-item>
                      </el-descriptions>
                      <div v-if="item.commentList && item.commentList.length > 0">
                        <div v-for="(comment, index) in item.commentList" :key="index">
                          <el-divider content-position="left">
                            <el-tag :type="approveTypeTag(comment.type)" size="small">{{commentType(comment.type)}}</el-tag>
                            <el-tag type="info" effect="plain" size="small">{{ comment.time }}</el-tag>
                          </el-divider>
                          <span>{{ comment.fullMessage }}</span>
                        </div>
                      </div>
                    </el-card>
                    <el-card v-if="item.activityType === 'endEvent'" class="box-card" shadow="hover">
                      {{ item.createTime }} 结束流程
                    </el-card>
                  </el-timeline-item>
                </el-timeline>
              </div>
            </el-col>
          </el-card>
        </el-tab-pane>


        <el-tab-pane label="流程跟踪" name="third">
          <ProcessViewer :taskId="form.wfTaskid"/>
        </el-tab-pane>

      </el-tabs>
    </el-drawer>
  </div>

</template>

<script>
import {
  listProcesswacth,
  getProcesswacth,
  delProcesswacth,
  addProcesswacth,
  updateProcesswacth,
  fetchFlowInstanceInfo
} from "@/api/processwacth/processwacth";

export default {

  name: "Processwacth",
  dicts: ['bus_appstate'],
  data() {
    return {
      flowInstanceVOList: [],
      activeName: 'first',
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
      // 流程监控表格数据
      processwacthList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        wfTaskid: null,
        wfWfname: null,
        wfBusinesskey: null,
        wfStarttime: null,
        wfState: null,
        wfApprtime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        wfTaskid: [
          {required: true, message: "流程实例taskid不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    lookMessage(row) {
      this.form = row;
      fetchFlowInstanceInfo(row.wfTaskid).then(response => {
        this.drawer = true;
        this.flowInstanceVOList = response.data;
      });
    },
    handleClose(done) {
    },
    handleClick(tab, event) {

    },
    /** 查询流程监控列表 */
    getList() {
      this.loading = true;
      listProcesswacth(this.queryParams).then(response => {
        this.processwacthList = response.rows;
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
        wfBusinesskey: null,
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加流程监控";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const wfWfid = row.wfWfid || this.ids
      getProcesswacth(wfWfid).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改流程监控";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.wfWfid != null) {
            updateProcesswacth(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProcesswacth(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除流程监控编号为"' + wfWfids + '"的数据项？').then(function () {
        return delProcesswacth(wfWfids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('processwacth/processwacth/export', {
        ...this.queryParams
      }, `processwacth_${new Date().getTime()}.xlsx`)
    },
    setIcon(val) {
      if (val) {
        return "el-icon-check";
      } else {
        return "el-icon-time";
      }
    },
    setColor(val) {
      if (val) {
        return "#2bc418";
      } else {
        return "#b3bdbb";
      }
    },
    approveTypeTag(val) {
      switch (val) {
        case '1':
          return 'success';
        case '2':
          return 'warning';
        case '3':
          return 'danger';
        case '4':
          return 'primary';
        case '5':
          return 'success';
        case '6':
          return 'danger';
        case '7':
          return 'info';
      }
    },
    commentType(val) {
      switch (val) {
        case '1':
          return '通过';
        case '2':
          return '退回';
        case '3':
          return '驳回';
        case '4':
          return '委派';
        case '5':
          return '转办';
        case '6':
          return '终止';
        case '7':
          return '撤回';
        case '8':
          return '拒绝';
        case '9':
          return '跳过';
        case '10':
          return '前加签';
        case '11':
          return '后加签';
        case '12':
          return '多实例加签';
        case '13':
          return '跳转';
        case '14':
          return '收回';
      }
    }
  }
}
</script>

<style scoped>.timeline-content p {
  margin: 5px 0;
}

.timeline-content strong {
  font-weight: bold;
  color: #333; /* 可以根据需要调整颜色 */
}

.el-card {
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.el-timeline-item__timestamp {
  font-size: 14px;
  color: #606266;
}

.el-timeline-item__content {
  padding-left: 20px;
}
</style>
