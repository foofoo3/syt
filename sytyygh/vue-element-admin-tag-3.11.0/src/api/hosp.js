import request from '@/utils/request'

export default {
    getHospList(page,limit,searchObj){
        return request({
            url: `/admin/hosp/hospital/list/${page}/${limit}`,
            method: 'get',
            params: searchObj
          })
    },

    findByDictCode(dictCode) {
        return request ({
        url: `/admin/cmn/dict/findByDictCode/${dictCode}`,
        method: 'get'
        })
    },

    findChildId(id) {
        return request ({
        url: `/admin/cmn/dict/findChildData/${id}`,
        method: 'get'
        })
    },

    updateStatus(id,status) {
        return request ({
        url: `/admin/hosp/hospital/updateHospStatus//${id}/${status}`,
        method: 'get'
        })
    },

    getHospById(id) {
        return request ({
        url: `/admin/hosp/hospital/showHospDetail/${id}`,
        method: 'get'
        })
    },

    getDeptByHoscode(hoscode) {
        return request ({
        url: `/admin/hosp/department/getDeptList/${hoscode}`,
        method: 'get'
        })
    },

    getScheduleRule(page,limit,hoscode,depcode) {
        return request ({
        url: `/admin/hosp/schedule/getScheduleRule/${page}/${limit}/${hoscode}/${depcode}`,
        method: 'get'
        })
    },

    getScheduleDetail(hoscode,depcode,workDate) {
        return request ({
        url: `/admin/hosp/schedule/getScheduleDetail/${hoscode}/${depcode}/${workDate}`,
        method: 'get'
        })
    }
}