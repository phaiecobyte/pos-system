import { Routes } from '@angular/router';
// Import LoginComponent ដែលយើងទើបតែបង្កើត
import { LoginComponent } from './features/auth/login';
import { DashboardComponent } from './features/dashboard/dashboard';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path:'dashboard',
    component: DashboardComponent
  }
  // TODO: ថ្ងៃក្រោយយើងនឹងបន្ថែម Route សម្រាប់ Dashboard ទីនេះ ដោយមាន AuthGuard ការពារ
];
