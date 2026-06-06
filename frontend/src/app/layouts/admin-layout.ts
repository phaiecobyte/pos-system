import { Component, ChangeDetectionStrategy } from '@angular/core';
import { SidebarComponent } from './sidebar';
import { TopbarComponent } from './topbar';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-admin-layout',
  imports: [SidebarComponent, TopbarComponent, RouterOutlet],
  template: `
    <div class="flex h-screen">
      <app-sidebar />

      <div class="flex flex-col flex-1">
        <app-topbar />

        <main class="flex-1 overflow-auto p-6">
          <router-outlet />
        </main>
      </div>
    </div>
  `,
  changeDetection: ChangeDetectionStrategy.Eager,
  styles: ``,
})
export class AdminLayoutComponent {}
