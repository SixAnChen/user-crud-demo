<template>
  <div class="home">
    <el-card style="margin: 0 20px">
      <div style="margin-bottom: 20px">
        <el-input size="medium" v-model="params.name" style="width: 200px" placeholder="请输入姓名按Enter键搜索"
                  @keyup.enter.native="load"></el-input>
        <el-button type="primary" style="margin-left: 10px" @click="load()">查询</el-button>
        <el-button type="warning" style="margin-left: 10px" @click="reset()">重置</el-button>
        <el-button type="success" style="margin-left: 10px" @click="save()">新增</el-button>
      </div>
      <el-table
          :data="tableData"
          max-height="400"
          style="width: 100%">
        <el-table-column
            prop="id"
            align="center"
            label="ID"
            fixed
            width="100">
        </el-table-column>
        <el-table-column
            prop="name"
            label="姓名"
            width="120">
        </el-table-column>
        <el-table-column
            prop="age"
            label="年龄"
            width="100">
        </el-table-column>
        <el-table-column
            prop="sex"
            label="性别"
            width="100">
        </el-table-column>
        <el-table-column
            prop="phone"
            label="电话"
            width="130">
        </el-table-column>
        <el-table-column
            prop="address"
            align="center"
            label="地址"
            width="200"
            show-overflow-tooltip>
        </el-table-column>
        <el-table-column
            prop="createtime"
            align="center"
            label="创建时间"
            width="200">
        </el-table-column>
        <el-table-column
            prop="updatetime"
            align="center"
            label="修改时间"
            width="200">
        </el-table-column>
        <el-table-column label="状态" align="center" width="100">
          <template slot-scope="scope">
            <el-switch
                @change="changeStatus(scope.row)"
                v-model="scope.row.status"
                active-color="#13ce66"
                inactive-color="#ff4949">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="380" fixed="right">
          <template slot-scope="scope">
            <el-button
                icon="el-icon-document"
                @click="handleDetail(scope.row)">查看
            </el-button>
            <el-button
                icon="el-icon-edit"
                type="primary"
                @click="handleEdit(scope.row)">编辑
            </el-button>
            <el-button
                icon="el-icon-delete"
                type="danger"
                @click="handleDelete(scope.row.id)">删除
            </el-button>
            <el-button
                icon="el-icon-refresh-left"
                type="warning"
                @click="handleResetPass(scope.row)">重置密码
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          style="margin-top: 20px"
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="params.pageNum"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="params.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>


      <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%" @close="handleCancel">
        <el-form :model="form" label-width="15%" :rules="rules" ref="form">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="form.name" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="年龄" prop="age">
            <el-input v-model="form.age" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-radio v-model="form.sex" label="男">男</el-radio>
            <el-radio v-model="form.sex" label="女">女</el-radio>
          </el-form-item>
          <el-form-item label="电话" prop="phone">
            <el-input v-model="form.phone" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="submit()">确 定</el-button>
        </div>
      </el-dialog>

      <el-dialog title="用户详情" :visible.sync="userInfoVisible" width="40%">
        <el-descriptions border column="1" size="medium">
          <el-descriptions-item label="ID">{{ form.id }}</el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-tickets"></i>
              卡号
            </template>
            {{ form.username }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-user"></i>
              用户名
            </template>
            {{ form.name }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-mobile-phone"></i>
              手机号
            </template>
            {{ form.phone }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-location-outline"></i>
              居住地
            </template>
            {{ form.address }}
          </el-descriptions-item>
          <el-descriptions-item label="年龄">{{ form.age }}</el-descriptions-item>
          <el-descriptions-item label="性别">
            <el-tag size="small">{{ form.sex }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="密码">{{ form.password }}</el-descriptions-item>
        </el-descriptions>
      </el-dialog>
    </el-card>


  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  name: 'HomeView',
  data() {
    var validatePhone = (rule, value, callback) => {
      if (/^1[34578]\d{9}$/.test(value) == false) {
        callback(new Error("请输入正确的手机号"));
      } else {
        callback();
      }
    }
    var validateAge = (rule, value, callback) => {
      if (/^[0-9]*$/.test(value) == false) {
        callback(new Error("请输入正确的年龄"));
      } else {
        callback();
      }
    }
    return {
      tableData: [],
      total: 0,
      params: {
        pageNum: 1,
        pageSize: 10,
        name: ''
      },
      dialogFormVisible: false,
      form: {},
      rules: {
        name: [
          {required: true, message: '请输入姓名', trigger: 'blur'}
        ],
        age: [
          {required: true, validator: validateAge, trigger: 'blur'}
        ],
        sex: [
          {required: true, message: '请输入性别', trigger: 'blur'}
        ],
        phone: [
          {required: true, validator: validatePhone, trigger: 'blur'}
        ]
      },
      userInfoVisible: false
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      request.get('/user/page', {params: this.params}).then(res => {
        if (res.code === '200') {
          this.tableData = res.data.list
          this.total = res.data.total
        }
      })
    },
    handleSizeChange(pageSize) {
      this.params.pageSize = pageSize;
      this.load();
    },
    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum;
      this.load();
    },
    reset() {
      this.params = {
        pageNum: 1,
        pageSize: 10,
        name: ''
      }
      this.load()
    },
    handleEdit(obj) {
      this.form = JSON.parse(JSON.stringify(obj)); //深拷贝
      this.dialogFormVisible = true;
    },
    submit() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          request.post("/user", this.form).then(res => {
            if (res.code === '200') {
              this.$message.success("操作成功")
              this.dialogFormVisible = false;
              this.load();
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    save() {
      this.form = {};
      this.dialogFormVisible = true;
    },
    handleDelete(id) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request.delete("/user/" + id).then(res => {
          if (res.code === '200') {
            this.load();
          } else {
            this.$message.error(res.msg)
          }
        })
      }).catch(() => {
        this.$message.info("已取消删除")
      });
    },
    handleCancel() {
      this.$refs.form.resetFields();
    },
    changeStatus(row) {
      request.post("/user", row).then(res => {
        if (res.code === '200') {
          this.$notify.success("操作成功")
          this.load();
        } else {
          this.$notify.error(res.msg)
        }
      })
    },
    handleResetPass(row) {
      this.$confirm('是否重置该用户密码，该操作不可逆, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request.put("/user/resetPass", row).then(res => {
          if (res.code === '200') {
            this.$notify.success("密码重置成功")
            this.load();
          } else {
            this.$notify.error(res.msg)
          }
        })
      }).catch(() => {
        this.$message.info("已取消操作")
      });
    },

    handleDetail(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.userInfoVisible = true
    }
  }
}
</script>
