import { Component, input } from '@angular/core';
import { Tenant } from '../../core/models/model';

@Component({
  selector: 'app-tenant-table',
  imports: [],
  template: `
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
        @if (tenants().length === 0) {
          <tr>
            <td colspan="4" class="p-8 text-center text-gray-500">No tenants found</td>
          </tr>
        } @else {
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
        }
      </tbody>
    </table>
  `,
  styles: ``,
})
export class TenantTableComponent {
  readonly tenants = input.required<Tenant[]>();
}
