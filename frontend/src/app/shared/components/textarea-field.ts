import { Component, input } from '@angular/core';

@Component({
  selector: 'app-textarea-field',
  imports: [],
  template: `
    <div class="flex flex-col gap-2">

  <label class="text-sm font-medium text-gray-700">
    {{ label() }}

    @if (required()) {
      <span class="text-red-500">*</span>
    }
  </label>

  <textarea
    [rows]="rows()"
    [placeholder]="placeholder()"
    class="w-full rounded-lg border border-gray-300
           px-4 py-3
           resize-none
           focus:border-green-600
           focus:ring-2
           focus:ring-green-200
           outline-none
           transition">
  </textarea>

</div>
  `,
  styles: ``,
})
export class TextareaFieldComponent {
  label = input.required<string>();
  placeholder = input('');
  required = input(false);

  rows = input(4);
}
