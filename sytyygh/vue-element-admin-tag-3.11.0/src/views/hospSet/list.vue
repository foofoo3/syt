<template>
    <div class="app-container">
        医院设置列表

        <el-form :inline="true" class="demo-form-inline">
            <el-form-item>
                <el-input v-model="searchObj.hosname" placeholder="医院名称"></el-input>
            </el-form-item>
            <el-form-item>
                <el-input v-model="searchObj.hoscode" placeholder="医院编号"></el-input>
            </el-form-item> 
    
            <el-button type="primary" @click="getList()">查询</el-button>
        </el-form>
        <div>
            <el-button type="danger" size="mini" @click="removeRows()">批量删除</el-button>
        </div>

        <el-table
            :data="list" style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="35"/>
            <el-table-column type="index" width="50" label="序号"/>
            <el-table-column prop="hosname" label="医院名称"/>
            <el-table-column prop="hoscode" label="医院编号"/>
            <el-table-column prop="apiUrl" label="api基础路径" width="200"/>
            <el-table-column prop="contactsName" label="联系人姓名"/>
            <el-table-column prop="contactsPhone" label="联系人手机"/>
            <el-table-column label="状态" width="80">
                <template slot-scope="scope">
                    {{ scope.row.status === 1 ? '可用' : '不可用'}}
                </template>
            </el-table-column>
            <el-table-column label="操作" width="280" align="center">
                <template slot-scope="scope">
                    <el-button type="danger"  size="mini" 
                    icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除
                    </el-button>
                    <el-button type="primary" v-if="scope.row.status == 1" size="mini" 
                    icon="el-icon-delete" @click="lockHospSet(scope.row.id,0)">锁定
                    </el-button>
                    <el-button type="danger" v-if="scope.row.status == 0" size="mini" 
                    icon="el-icon-delete" @click="lockHospSet(scope.row.id,1)">取消锁定
                    </el-button>
                    <router-link :to="'/hospSet/edit/'+scope.row.id">
                        <el-button type="primary" size="mini" icon="el-icon-edit"></el-button>
                    </router-link>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页 -->
        <el-pagination
            :current-page="page"
            :page-size="limit"
            :total="total"
            style="padding: 30px 0; text-align: center"
            layout="total,prev, pager, next,jumper"
            @current-change="getList"
            />
    </div>
</template>
<script>
import hospset from '@/api/hospset'
export default {
    data() {
        return {
            current:1,
            limit:3,
            searchObj:{},
            list:[],
            total:0,
            multipleSelection: []
        };
    },
    created(){
        this.getList()
    },
    methods:{
        getList(page=1){
            this.current = page
            hospset.getHospSetList(this.current,this.limit,this.searchObj)
            .then(response => {
                this.list = response.data.data.records
                this.total = response.data.data.total
                console.log(response)
                console.log(this.current)
            })
            .catch(error => {
                console.log(error)
            })
        },
        removeDataById(id){
            this.$confirm('此操作将永久删除医院设置信息, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
            }).then(() => {
                hospset.deleteHospSet(id)
                    .then(response => {
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        })
                        
                        this.getList(1)
                        
                    })
                
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });          
            });
            
        },
        removeRows(){
            this.$confirm('此操作将永久删除医院设置信息, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
            }).then(() => {
                var idList = []
                for (var i = 0;i < this.multipleSelection.length;i++){
                    var obj = this.multipleSelection[i]
                    var id = obj.id
                    idList.push(id)
                }
                hospset.batchHospSet(idList)
                    .then(response => {
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        })
                        this.getList(1)
                    })
                
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });          
            });
        },
        handleSelectionChange(selection){
            this.multipleSelection = selection
        },
        lockHospSet(id,status){
            hospset.lockHospSet(id,status)
            .then(response => {
                this.getList()
            })
        }
    }
}
</script>
