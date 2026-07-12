import { Component, input } from '@angular/core';

@Component({
  selector: 'app-select-field',
  imports: [],
  template: `
    <div class="flex flex-col gap-2">
      <label class="text-sm font-medium text-gray-700">
        {{ label() }}

        @if (required()) {
          <span class="text-red-500">*</span>
        }
      </label>

      <select
        class="w-full rounded-lg border border-gray-300
           px-4 py-3
           bg-white
           focus:border-green-600
           focus:ring-2
           focus:ring-green-200
           outline-none
           transition"
      >
        <option value="" disabled selected>
          {{ placeholder() }}
        </option>

        @for (item of options(); track item.value) {
          <option [value]="item.value">
            {{ item.label }}
          </option>
        }
      </select>
    </div>
  `,
  styles: ``,
})
export class SelectFieldComponent {
  label = input.required<string>();
  required = input(false);

  placeholder = input('Select');

  options = input<
    {
      label: string;
      value: string;
    }[]
  >([]);
}
