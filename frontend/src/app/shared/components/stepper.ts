import { Component, input } from '@angular/core';

@Component({
  selector: 'app-stepper',
  imports: [],
  template: `
    <div class="flex flex-row items-start lg:flex-col lg:gap-4">

  @for(step of steps(); track $index){

    <div class="flex flex-col items-center text-center gap-2 lg:flex-row lg:items-start lg:text-left lg:gap-4">

      <!-- Circle -->
      <div
        class="w-10 h-10 shrink-0 rounded-full flex items-center justify-center font-bold transition-all"

        [class.bg-yellow-400]="currentStep() > $index"
        [class.text-green-900]="currentStep() > $index"

        [class.bg-green-600]="currentStep() === $index + 1"
        [class.text-white]="currentStep() === $index + 1"

        [class.border-2]="currentStep() < $index + 1"
        [class.border-white]="currentStep() < $index + 1">

        {{ $index + 1 }}

      </div>

      <!-- Content -->
      <div class="lg:flex-1 lg:pt-1">

        <h3
          class="text-xs lg:text-base font-semibold"

          [class.text-white]="currentStep() >= $index + 1"
          [class.text-green-200]="currentStep() < $index + 1">

          {{ step.title }}

        </h3>

        @if(step.description){

          <p class="hidden lg:block text-sm text-green-200">

            {{ step.description }}

          </p>

        }

      </div>

    </div>

    @if(!$last){

      <div
        class="flex-1 mt-5 border-t-2 lg:flex-none lg:mt-0 lg:ml-5 lg:w-auto lg:h-8 lg:border-t-0 lg:border-l-2"

        [class.border-yellow-400]="currentStep() > $index + 1"
        [class.border-green-700]="currentStep() <= $index + 1">

      </div>

    }

  }

</div>
  `,
  styles: ``,
})
export class StepperComponent {
  currentStep = input(1);
  steps = input<Step[]>([]);
}
export interface Step {
  title: string;
  description?: string;
}