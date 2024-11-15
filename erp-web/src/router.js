import Vue from 'vue'
import Router from 'vue-router'

// import Login from './components/Login.vue'
const Login = () => import(/* webpackChunkName: "login_home_welcome" */ './components/Login.vue')
// import Home from './components/Home.vue'
const Home = () => import(/* webpackChunkName: "login_home_welcome" */ './components/Home.vue')
// import Welcome from './components/Welcome.vue'
const Welcome = () => import(/* webpackChunkName: "login_home_welcome" */ './components/Welcome.vue')

const UserManagement = () => import(/* webpackChunkName: "Settings" */ './components/settings/UserManagement.vue')
const LogManagement = () => import(/* webpackChunkName: "Settings" */ './components/settings/LogManagement.vue')
const SystemConfiguration = () => import(/* webpackChunkName: "Settings" */ './components/settings/SystemConfiguration.vue')
const Category = () => import(/* webpackChunkName: "Settings" */ './components/settings/Category.vue')
const UnitManagement = () => import(/* webpackChunkName: "Settings" */ './components/settings/UnitManagement.vue')
const SettlementManagement = () => import(/* webpackChunkName: "Settings" */ './components/settings/SettlementManagement.vue')

const SettlementAccount = () => import(/* webpackChunkName: "Basic" */ './components/basic/SettlementAccount.vue')
const WarehouseManagement = () => import(/* webpackChunkName: "Basic" */ './components/basic/WarehouseManagement.vue')
const EmployeeManagement = () => import(/* webpackChunkName: "Basic" */ './components/basic/EmployeeManagement.vue')
const CustomerManagement = () => import(/* webpackChunkName: "Basic" */ './components/basic/CustomerManagement.vue')
const SupplierManagement = () => import(/* webpackChunkName: "Basic" */ './components/basic/SupplierManagement.vue')
const ProductManagement = () => import(/* webpackChunkName: "Basic" */ './components/basic/ProductManagement.vue')

const PurchaseList = () => import(/* webpackChunkName: "Purchase" */ './components/purchase/List.vue')
const PurchaseSave = () => import(/* webpackChunkName: "Purchase" */ './components/purchase/Save.vue')

const RefundList = () => import(/* webpackChunkName: "Refund" */ './components/refund/List.vue')
const RefundSave = () => import(/* webpackChunkName: "Refund" */ './components/refund/Save.vue')

const OrderList = () => import(/* webpackChunkName: "Order" */ './components/order/List.vue')
const OrderSave = () => import(/* webpackChunkName: "Order" */ './components/order/Save.vue')

const SellList = () => import(/* webpackChunkName: "Sell" */ './components/sell/List.vue')
const SellSave = () => import(/* webpackChunkName: "Sell" */ './components/sell/Save.vue')

const ReturnedList = () => import(/* webpackChunkName: "Returned" */ './components/returned/List.vue')
const ReturnedSave = () => import(/* webpackChunkName: "Returned" */ './components/returned/Save.vue')

const TransferList = () => import(/* webpackChunkName: "Transfer" */ './components/transfer/List.vue')
const TransferSave = () => import(/* webpackChunkName: "Transfer" */ './components/transfer/Save.vue')

const StoreList = () => import(/* webpackChunkName: "Store" */ './components/store/List.vue')
const StoreSave = () => import(/* webpackChunkName: "Store" */ './components/store/Save.vue')

const CheckoutList = () => import(/* webpackChunkName: "Checkout" */ './components/checkout/List.vue')
const CheckoutSave = () => import(/* webpackChunkName: "Checkout" */ './components/checkout/Save.vue')

const CollectionList = () => import(/* webpackChunkName: "Collection" */ './components/collection/List.vue')
const CollectionSave = () => import(/* webpackChunkName: "Collection" */ './components/collection/Save.vue')

const PaymentList = () => import(/* webpackChunkName: "Payment" */ './components/payment/List.vue')
const PaymentSave = () => import(/* webpackChunkName: "Payment" */ './components/payment/Save.vue')

const IncomeList = () => import(/* webpackChunkName: "Income" */ './components/income/List.vue')
const IncomeSave = () => import(/* webpackChunkName: "Income" */ './components/income/Save.vue')

const ExpenseList = () => import(/* webpackChunkName: "Expense" */ './components/expense/List.vue')
const ExpenseSave = () => import(/* webpackChunkName: "Expense" */ './components/expense/Save.vue')

const AnalysisPurchaseDetail = () => import(/* webpackChunkName: "Analysis" */ './components/analysis/PurchaseDetail.vue')
const AnalysisPurchaseProductSummary = () => import(/* webpackChunkName: "Analysis" */ './components/analysis/PurchaseProductSummary.vue')
const AnalysisPurchaseSupplierSummary = () => import(/* webpackChunkName: "Analysis" */ './components/analysis/PurchaseSupplierSummary.vue')
const AnalysisSaleDetail = () => import(/* webpackChunkName: "Analysis" */ './components/analysis/SaleDetail.vue')
const AnalysisSaleProductSummary = () => import(/* webpackChunkName: "Analysis" */ './components/analysis/SaleProductSummary.vue')
const AnalysisSaleCustomerSummary = () => import(/* webpackChunkName: "Analysis" */ './components/analysis/SaleCustomerSummary.vue')
const AnalysisStockDetail = () => import(/* webpackChunkName: "Analysis" */ './components/analysis/StockDetail.vue')
const AnalysisStockAmount = () => import(/* webpackChunkName: "Analysis" */ './components/analysis/StockAmount.vue')
const AnalysisStockSummary = () => import(/* webpackChunkName: "Analysis" */ './components/analysis/StockSummary.vue')
const AnalysisAccountDetail = () => import(/* webpackChunkName: "Analysis" */ './components/analysis/AccountDetail.vue')
const AnalysisReceivableDetail = () => import(/* webpackChunkName: "Analysis" */ './components/analysis/ReceivableDetail.vue')
const AnalysisPayableDetail = () => import(/* webpackChunkName: "Analysis" */ './components/analysis/PayableDetail.vue')
const AnalysisReceivableCustomer = () => import(/* webpackChunkName: "Analysis" */ './components/analysis/ReceivableCustomer.vue')
const AnalysisPayableSupplier = () => import(/* webpackChunkName: "Analysis" */ './components/analysis/PayableSupplier.vue')
const AnalysisFlowDetail = () => import(/* webpackChunkName: "Analysis" */ './components/analysis/FlowDetail.vue')

Vue.use(Router)

const router = new Router({
  routes: [
    { path: '/', redirect: '/login' },
    { path: '/login', component: Login },
    {
      path: '/home',
      component: Home,
      redirect: '/welcome',
      children: [
        { path: '/welcome', component: Welcome },
        { path: '/settings/userManagement', component: UserManagement },
        { path: '/settings/logManagement', component: LogManagement },
        { path: '/settings/systemConfiguration', component: SystemConfiguration },
        { path: '/settings/category', component: Category },
        { path: '/settings/unitManagement', component: UnitManagement },
        { path: '/settings/settlementManagement', component: SettlementManagement },
        { path: '/basic/settlementAccount', component: SettlementAccount },
        { path: '/basic/warehouseManagement', component: WarehouseManagement },
        { path: '/basic/employeeManagement', component: EmployeeManagement },
        { path: '/basic/customerManagement', component: CustomerManagement },
        { path: '/basic/supplierManagement', component: SupplierManagement },
        { path: '/basic/productManagement', component: ProductManagement },
        { path: '/purchase/list', component: PurchaseList },
        { path: '/purchase/save', component: PurchaseSave },
        { path: '/refund/list', component: RefundList },
        { path: '/refund/save', component: RefundSave },
        { path: '/order/list', component: OrderList },
        { path: '/order/save', component: OrderSave },
        { path: '/sell/list', component: SellList },
        { path: '/sell/save', component: SellSave },
        { path: '/returned/list', component: ReturnedList },
        { path: '/returned/save', component: ReturnedSave },
        { path: '/transfer/list', component: TransferList },
        { path: '/transfer/save', component: TransferSave },
        { path: '/store/list', component: StoreList },
        { path: '/store/save', component: StoreSave },
        { path: '/checkout/list', component: CheckoutList },
        { path: '/checkout/save', component: CheckoutSave },
        { path: '/collection/list', component: CollectionList },
        { path: '/collection/save', component: CollectionSave },
        { path: '/payment/list', component: PaymentList },
        { path: '/payment/save', component: PaymentSave },
        { path: '/income/list', component: IncomeList },
        { path: '/income/save', component: IncomeSave },
        { path: '/expense/list', component: ExpenseList },
        { path: '/expense/save', component: ExpenseSave },
        { path: '/analysis/purchaseDetail', component: AnalysisPurchaseDetail },
        { path: '/analysis/purchaseProductSummary', component: AnalysisPurchaseProductSummary },
        { path: '/analysis/purchaseSupplierSummary', component: AnalysisPurchaseSupplierSummary },
        { path: '/analysis/saleDetail', component: AnalysisSaleDetail },
        { path: '/analysis/saleProductSummary', component: AnalysisSaleProductSummary },
        { path: '/analysis/saleCustomerSummary', component: AnalysisSaleCustomerSummary },
        { path: '/analysis/stockDetail', component: AnalysisStockDetail },
        { path: '/analysis/stockAmount', component: AnalysisStockAmount },
        { path: '/analysis/stockSummary', component: AnalysisStockSummary },
        { path: '/analysis/accountDetail', component: AnalysisAccountDetail },
        { path: '/analysis/receivableDetail', component: AnalysisReceivableDetail },
        { path: '/analysis/payableDetail', component: AnalysisPayableDetail },
        { path: '/analysis/receivableCustomer', component: AnalysisReceivableCustomer },
        { path: '/analysis/payableSupplier', component: AnalysisPayableSupplier },
        { path: '/analysis/flowDetail', component: AnalysisFlowDetail }
      ]
    }
  ]
})

// 挂载路由导航守卫
router.beforeEach((to, from, next) => {
  // to 将要访问的路径
  // from 代表从哪个路径跳转而来
  // next 是一个函数，表示放行
  //     next()  放行    next('/login')  强制跳转
  const tokenStr = window.sessionStorage.getItem('token')
  if (!tokenStr) {
    if (to.path === '/login') { // 未登录且已经在Login页了，直接放行
      return next()
    }

    return next('/login')
  }

  if (to.path === '/login') {
    return next('/home')
  }
  next()
})

export default router
