import { Routes } from '@angular/router';
import { AdminLayoutComponent } from './layouts/admin-layout';


export const routes: Routes = [
     {
        path: '',
        loadComponent: () =>
        import('./features/register/register').then(c => c.Register),
    },
    {
        path: '',
        component: AdminLayoutComponent,
        children: [
            {
                path: 'dashboard',
                loadComponent: () => import('./features/dashboard/dashboard').then(c=>c.DashboardComponent)
            },
            {
                path: 'tenants',
                loadChildren: () => import('./features/tenants/tenant.route').then(m => m.TENANT_ROUTES)
            },
            {
                path: '',
                redirectTo: 'dashboard',
                pathMatch: 'full'
            }
        ]
    }
];
