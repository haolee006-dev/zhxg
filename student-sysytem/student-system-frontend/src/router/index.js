import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/layout/index.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index.vue'),
      meta: { title: '数据概览', icon: 'el-icon-s-data' }
    }]
  },
  {
    path: '/student',
    component: Layout,
    redirect: '/student/list',
    name: 'Student',
    meta: { title: '学员信息管理', icon: 'el-icon-user' },
    children: [
      {
        path: 'list',
        name: 'StudentList',
        component: () => import('@/views/student/list.vue'),
        meta: { title: '学生档案' }
      },
      {
        path: 'counselor',
        name: 'Counselor',
        component: () => import('@/views/student/counselor.vue'),
        meta: { title: '辅导员管理' }
      },
      {
        path: 'record',
        name: 'StudentRecord',
        component: () => import('@/views/student/record.vue'),
        meta: { title: '档案记录' }
      },
      {
        path: 'disciplinary',
        name: 'Disciplinary',
        component: () => import('@/views/student/disciplinary.vue'),
        meta: { title: '违纪处分' }
      }
    ]
  },
  {
    path: '/welcome',
    component: Layout,
    redirect: '/welcome/registration',
    name: 'Welcome',
    meta: { title: '迎新管理', icon: 'el-icon-s-flag' },
    children: [
      {
        path: 'registration',
        name: 'WelcomeRegistration',
        component: () => import('@/views/welcome/registration.vue'),
        meta: { title: '预报到登记' }
      },
      {
        path: 'process',
        name: 'WelcomeProcess',
        component: () => import('@/views/welcome/process.vue'),
        meta: { title: '报到流程' }
      }
    ]
  },
  {
    path: '/student-work',
    component: Layout,
    redirect: '/student-work/card',
    name: 'StudentWork',
    meta: { title: '学生工作', icon: 'el-icon-s-order' },
    children: [
      {
        path: 'card',
        name: 'StudentCard',
        component: () => import('@/views/student-work/card.vue'),
        meta: { title: '学生证办理' }
      },
      {
        path: 'scholarship',
        name: 'Scholarship',
        component: () => import('@/views/student-work/scholarship.vue'),
        meta: { title: '奖学金管理' }
      },
      {
        path: 'financial-aid',
        name: 'FinancialAid',
        component: () => import('@/views/student-work/financial-aid.vue'),
        meta: { title: '资助管理' }
      },
      {
        path: 'evaluation',
        name: 'Evaluation',
        component: () => import('@/views/student-work/evaluation.vue'),
        meta: { title: '学情测评' }
      },
      {
        path: 'health',
        name: 'Health',
        component: () => import('@/views/student-work/health.vue'),
        meta: { title: '健康档案' }
      },
      {
        path: 'notification',
        name: 'Notification',
        component: () => import('@/views/student-work/notification.vue'),
        meta: { title: '学生通报' }
      }
    ]
  },
  {
    path: '/dormitory',
    component: Layout,
    redirect: '/dormitory/list',
    name: 'Dormitory',
    meta: { title: '宿舍管理', icon: 'el-icon-s-home' },
    children: [
      {
        path: 'list',
        name: 'DormitoryList',
        component: () => import('@/views/dormitory/list.vue'),
        meta: { title: '宿舍信息' }
      },
      {
        path: 'check',
        name: 'DormitoryCheck',
        component: () => import('@/views/dormitory/check.vue'),
        meta: { title: '卫生检查' }
      },
      {
        path: 'repair',
        name: 'DormitoryRepair',
        component: () => import('@/views/dormitory/repair.vue'),
        meta: { title: '宿舍报修' }
      },
      {
        path: 'violation',
        name: 'DormitoryViolation',
        component: () => import('@/views/dormitory/violation.vue'),
        meta: { title: '宿舍违纪' }
      },
      {
        path: 'holiday-stay',
        name: 'HolidayStay',
        component: () => import('@/views/dormitory/holiday-stay.vue'),
        meta: { title: '假期留校' }
      }
    ]
  },
  {
    path: '/leave-school',
    component: Layout,
    redirect: '/leave-school/list',
    name: 'LeaveSchool',
    meta: { title: '离校管理', icon: 'el-icon-s-promotion' },
    children: [
      {
        path: 'list',
        name: 'LeaveSchoolList',
        component: () => import('@/views/leave-school/list.vue'),
        meta: { title: '离校办理' }
      },
      {
        path: 'graduation',
        name: 'Graduation',
        component: () => import('@/views/leave-school/graduation.vue'),
        meta: { title: '毕业生管理' }
      }
    ]
  },
  {
    path: '/analysis',
    component: Layout,
    redirect: '/analysis/student',
    name: 'Analysis',
    meta: { title: '数据分析', icon: 'el-icon-s-marketing' },
    children: [
      {
        path: 'student',
        name: 'StudentAnalysis',
        component: () => import('@/views/analysis/student.vue'),
        meta: { title: '学生画像' }
      },
      {
        path: 'dashboard',
        name: 'AnalysisDashboard',
        component: () => import('@/views/analysis/dashboard.vue'),
        meta: { title: '数据报表' }
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.path === '/login') {
    next()
  } else {
    if (!token) {
      next('/login')
    } else {
      next()
    }
  }
})

export default router
