import request from '@/utils/request'

export default {
    getHospSetList(current,limit,searchObj){
        return request({
            url: `/admin/hosp/hospital/findPageHospSet/${current}/${limit}`,
            method: 'post',
            data:searchObj
          })
    },
    deleteHospSet(id){
        return request({
            url: `/admin/hosp/hospital/${id}`,
            method: 'delete',
          })
    },
    batchHospSet(idList){
        return request({
            url: `/admin/hosp/hospital/batchRemove`,
            method: 'delete',
            data:idList
          })
    },
    lockHospSet(id,status){
        return request({
            url: `/admin/hosp/hospital/lockHospitalSet/${id}/${status}`,
            method: 'put' 
          })
    },
    saveHospSet(hospitalSet){
        return request({
            url: `/admin/hosp/hospital/saveHospitalSet`,
            method: 'post',
            data:hospitalSet
          })
    },
    getHospSet(id){
        return request({
            url: `/admin/hosp/hospital/getHospSet/${id}`,
            method: 'get'
          })
    },
    updateHospSet(hospitalSet){
        return request({
            url: `/admin/hosp/hospital/updateHospitalSet`,
            method: 'post',
            data:hospitalSet
          })
    }
}