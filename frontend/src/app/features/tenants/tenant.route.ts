import { Routes } from '@angular/router';

export const TENANT_ROUTES: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./tenant-list')
        .then(c => c.TenantListComponent)
  },

  {
    path: 'create',
    loadComponent: () =>
      import('./tenant-form')
        .then(c => c.TenantFormComponent)
  },

  {
    path: ':id',
    loadComponent: () =>
      import('./tenant-form-detail')
        .then(c => c.TenantFormDetailComponent)
  },

  {
    path: ':id/edit',
    loadComponent: () =>
      import('./tenant-form')
        .then(c => c.TenantFormComponent)
  }
];