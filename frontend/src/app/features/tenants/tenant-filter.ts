import { Component, input, output } from '@angular/core';

@Component({
  selector: 'app-tenant-filter',
  standalone: true,
  template: `
    <div class="flex items-center gap-3 mb-4">

      <input
        type="text"
        placeholder="Search tenant..."
        class="w-72 border rounded-lg px-3 py-2"
        [value]="searchTerm()"
        (input)="onInput($event)"
      />

      <button
        (click)="createClicked.emit()"
        class="px-4 py-2 rounded-lg bg-blue-600 text-white"
      >
        Add Tenant
      </button>

    </div>
  `,
})
export class TenantFilterComponent {

  readonly searchTerm = input('');

  readonly searchChanged = output<string>();

  readonly createClicked = output<void>();

  onInput(event: Event) {

    const value =
      (event.target as HTMLInputElement).value;

    this.searchChanged.emit(value);

  }
}