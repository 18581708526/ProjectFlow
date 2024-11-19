<template>
  <div>
    <el-input
      v-model="searchQuery"
      placeholder="请输入审批人姓名"
      @keyup.enter.native="handleSearch"
      @focus="showTable = true"
    >
      <i slot="suffix" class="el-input__icon el-icon-search" @click="handleSearch"></i>
    </el-input>
    <el-dialog
      title="选择审批人"
      :visible.sync="showTable"
      width="50%"
      append-to-body
    >
      <el-table
        :data="filteredUsers"
        @row-click="selectUser"
        highlight-current-row
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column prop="userId" label="人员id"></el-table-column>
        <el-table-column prop="name" label="人员姓名"></el-table-column>
        <el-table-column prop="deptName" label="人员部门"></el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showTable = false">取 消</el-button>
        <el-button type="primary" @click="confirmSelection">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {listtoSersch} from "@/api/system/user";

export default {
  data() {
    return {
      searchQuery: '',
      showTable: false,
      users: [],
      selectedUser: null
    };
  },
  computed: {
    filteredUsers() {
      return this.users.filter(user =>
        user.name.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    }
  },
  methods: {
    handleSearch() {
      this.showTable = true;
      this.fetchUsers();
    },
    fetchUsers() {
      // 假设你有一个 API 可以获取用户列表
      listtoSersch()
        .then(response => {
          this.users = response.rows;
          this.total = response.total;
        })
        .catch(error => {
        console.error(error);
      });
    },
    selectUser(row) {
      this.selectedUser = row;
    },
    confirmSelection() {
      if (this.selectedUser) {
        this.$emit('user-selected', this.selectedUser);
        this.searchQuery = this.selectedUser.name;
      }
      this.showTable = false;
    }
  },
  watch: {
    showTable(val) {
      if (val) {
        this.fetchUsers();
      }
    }
  }
};
</script>

<style scoped>.el-input__icon {
  cursor: pointer;
}
</style>
