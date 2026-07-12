import { Component, input } from '@angular/core';

@Component({
  selector: 'app-text-field',
  imports: [],
  template: `
    <div class="flex flex-col gap-2">
      <label class="text-sm font-medium text-gray-700">
        {{ label() }}

        @if (required()) {
          <span class="text-red-500">*</span>
        }
      </label>

      <input
        [type]="type()"
        [placeholder]="placeholder()"
        class="w-full rounded-lg border border-gray-300 px-4 py-3
           focus:border-green-600
           focus:ring-2
           focus:ring-green-200
           outline-none
           transition"
      />
    </div>
  `,
  styles: ``,
})
export class TextFieldComponent {
  label = input.required<string>();
  placeholder = input('');
  type = input('text');
  required = input(false);
}
