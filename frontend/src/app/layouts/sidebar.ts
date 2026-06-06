import { Component, signal, ChangeDetectionStrategy } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  imports: [RouterLink, RouterLinkActive],
  template: `
    <div class="w-64 h-screen border-r bg-white">
      <div class="p-6">
        <h1 class="text-xl font-bold">POS Admin</h1>
      </div>

      <nav class="px-4">
        @for (item of menuItems(); track item.route) {
          <a
            [routerLink]="item.route"
            routerLinkActive="bg-blue-100 text-blue-600"
            class="block px-4 py-3 rounded-lg mb-2"
          >
            {{ item.label }}
          </a>
        }
      </nav>
    </div>
  `,
  changeDetection: ChangeDetectionStrategy.Eager,
  styles: ``,
})
export class SidebarComponent {
  menuItems = signal([
    {
      label: 'Dashboard',
      icon: 'dashboard',
      route: '/dashboard',
    },
    {
      label: 'Tenants',
      icon: 'store',
      route: '/tenants',
    },
    {
      label: 'Revenue',
      icon: 'payments',
      route: '/revenue',
    },
    {
      label: 'Audit Logs',
      icon: 'history',
      route: '/audit-logs',
    },
    {
      label: 'Settings',
      icon: 'settings',
      route: '/settings',
    },
  ]);
}
