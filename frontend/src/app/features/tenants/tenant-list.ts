import { Component, inject, OnInit, signal, ChangeDetectionStrategy } from '@angular/core';
import { Tenant } from '../../core/models/model';
import { TenantApiService } from '../../core/api/tenant-api';
import { Router } from '@angular/router';

@Component({
  selector: 'app-tenant-datatable',
  standalone: true,
  imports: [],
  template: `
    <button (click)="goToCreate()" class="px-4 py-2 rounded-lg bg-blue-600 text-white">
      Add Tenant
    </button>
    <div class="rounded-xl border bg-white">
      <table class="w-full">
        <thead>
          <tr class="border-b">
            <th class="p-3 text-left">Code</th>

            <th class="p-3 text-left">Business</th>

            <th class="p-3 text-left">Type</th>

            <th class="p-3 text-left">Status</th>
          </tr>
        </thead>

        <tbody>
          @for (tenant of tenants(); track tenant.id) {
            <tr class="border-b">
              <td class="p-3">
                {{ tenant.code }}
              </td>

              <td class="p-3">
                {{ tenant.businessName }}
              </td>

              <td class="p-3">
                {{ tenant.businessTypeCode }}
              </td>

              <td class="p-3">
                <span class="px-2 py-1 rounded-full text-xs bg-green-100 text-green-700">
                  {{ tenant.status }}
                </span>
              </td>
            </tr>
          }
        </tbody>
      </table>
      <div class="flex justify-between mt-4">
        <button (click)="previousPage()" [disabled]="page() === 0">Previous</button>

        <span>
          Page {{ page() + 1 }}
          of
          {{ totalPages() }}
        </span>

        <button (click)="nextPage()" [disabled]="page() + 1 >= totalPages()">Next</button>
      </div>
    </div>
  `,
  changeDetection: ChangeDetectionStrategy.Eager,
  styles: [``],
})
export class TenantListComponent implements OnInit {
  readonly tenants = signal<Tenant[]>([]);
  readonly loading = signal(false);
  readonly page = signal(0);
  readonly size = signal(10);
  readonly totalElements = signal(0);
  readonly totalPages = signal(0);

  private readonly router = inject(Router);

  tenantApiService = inject(TenantApiService);

  ngOnInit() {
    this.loadTenants();
  }

  loadTenants() {
    this.loading.set(true);

    this.tenantApiService.list(this.page(), this.size()).subscribe({
      next: (response) => {
        this.tenants.set(response.data.content);

        this.totalElements.set(response.data.totalElements);

        this.totalPages.set(response.data.totalPages);

        this.loading.set(false);
      },

      error: () => {
        this.loading.set(false);
      },
    });
  }
  nextPage() {
    if (this.page() + 1 >= this.totalPages()) {
      return;
    }

    this.page.update((v) => v + 1);

    this.loadTenants();
  }
  previousPage() {
    if (this.page() === 0) {
      return;
    }

    this.page.update((v) => v - 1);

    this.loadTenants();
  }

  goToCreate() {
    this.router.navigate(['/tenants/create']);
  }
}
