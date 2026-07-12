import { Component, OnInit } from '@angular/core';
import { TextFieldComponent } from '../../shared/components/text-field';
import { SelectFieldComponent } from '../../shared/components/select-field';
import { StepperComponent } from '../../shared/components/stepper';

@Component({
  selector: 'app-register',
  standalone: true,
  template: `
    <div class="min-h-screen bg-gray-100 flex items-center justify-center p-2 sm:p-4 lg:p-8">
      <div
        class="w-full max-w-7xl bg-white rounded-3xl shadow-sm overflow-hidden
               flex flex-col lg:flex-row"
      >
        <!-- Left Panel -->
        <div class="w-full lg:w-80 bg-green-800 text-white p-8 flex flex-col">
          <h1 class="text-3xl font-bold mb-2 text-center" style="font-family: Moulpali;">
            ផៃអេកូបៃ
          </h1>

          <p class="text-green-100 text-sm mb-10 text-center">
            បង្កើតគណនីអាជីវកម្មថ្មី កំណត់ម្ចាស់គណនី និងរៀបចំខ្លួនសម្រាប់ប្រតិបត្តិការ POS
          </p>

          <div class="space-y-4">
            <app-stepper [steps]="steps" [currentStep]="currentStep"> </app-stepper>
          </div>
        </div>

        <!-- Right Panel -->
        <div class="flex-1 p-6 md:p-8 lg:p-12">
          <h2 class="text-2xl md:text-3xl font-bold mb-8">ចុះឈ្នោះប្រើប្រាស់ប្រព័ន្ធ</h2>
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4 md:gap-6">
            @if (currentStep === 1) {
              <!--Step 1: Business Information -->
              <app-text-field
                label="ឈ្មោះអាជីវកម្មជាភាសារអង់គ្លេស"
                placeholder="PHAI Coffee Shop"
                [required]="true"
              />
              <app-text-field
                label="ឈ្មោះអាជីវកម្មជាភាសារខ្មែរ"
                placeholder="ហាងកាហ្វេផៃ"
                [required]="false"
              />

              <app-select-field
                label="ប្រភេទអាជីវកម្ម"
                placeholder="ជ្រើសរើសប្រភេទអាជីវកម្ម"
                [required]="true"
                [options]="businessTypes"
              />

              <app-text-field label="កូត" placeholder="PHAI-001" [required]="false" />

              <app-select-field
                label="ខេត្ត/ក្រុង"
                placeholder="ជ្រើងរើសខេត្តក្រុង"
                [required]="true"
                [options]="provinces"
              />

              <app-text-field
                label="អាស័យដ្ឋាន"
                placeholder="វិថីកែវចិន្តា #ផ្ទះលេខF14 សង្កាត់ជ្រោយចង្វារ ខណ្ឌជ្រោយចង្វារ"
                [required]="false"
              />
            } @else if (currentStep === 2) {
              <!-- Step 2: Administrator Account -->
              <app-text-field label="គោត្តនាម" placeholder="ផល" [required]="true" />
              <app-text-field label="នាមខ្លួន" placeholder="ផៃ" [required]="true" />
              <app-text-field label="លេខទូរស័ព្ទ" placeholder="0965799628" [required]="false" />
              <app-text-field
                label="អាស័យដ្ឋានអ៊ីម៉ែល"
                placeholder="phaiecobyte@gmail.com"
                [required]="false"
              />
              <app-text-field
                label="ឈ្មោះអ្នកប្រើប្រាស់"
                placeholder="phalphai"
                [required]="true"
              />
              <app-text-field
                label="ពាក្យសម្ងាត់"
                placeholder="****"
                [required]="true"
                type="password"
              />
            }
          </div>
            @if (currentStep === 3) {
              <div class="space-y-8">
                <!-- Business Information -->
                <div class="rounded-xl border border-gray-200 p-6">
                  <div class="flex items-center justify-between mb-6">
                    <h3 class="text-lg font-semibold">🏢 Business Information</h3>

                    <button (click)="currentStep = 1" class="text-green-700 hover:underline">
                      Edit
                    </button>
                  </div>

                  <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div>
                      <p class="text-sm text-gray-500">Business Name</p>
                      <p class="font-medium">PHAI Coffee Shop</p>
                    </div>

                    <div>
                      <p class="text-sm text-gray-500">Business Type</p>
                      <p class="font-medium">Retail</p>
                    </div>

                    <div>
                      <p class="text-sm text-gray-500">Owner</p>
                      <p class="font-medium">ផល ផៃ</p>
                    </div>

                    <div>
                      <p class="text-sm text-gray-500">Phone</p>
                      <p class="font-medium">0965799628</p>
                    </div>

                    <div>
                      <p class="text-sm text-gray-500">Email</p>
                      <p class="font-medium">phaiecobyte@gmail.com</p>
                    </div>

                    <div>
                      <p class="text-sm text-gray-500">Province</p>
                      <p class="font-medium">Phnom Penh</p>
                    </div>

                    <div class="md:col-span-2">
                      <p class="text-sm text-gray-500">Address</p>
                      <p class="font-medium">Keo Chenda, Chroy Changvar, Phnom Penh</p>
                    </div>
                  </div>
                </div>

                <!-- Administrator -->
                <div class="rounded-xl border border-gray-200 p-6">
                  <div class="flex items-center justify-between mb-6">
                    <h3 class="text-lg font-semibold">👤 Administrator Account</h3>

                    <button (click)="currentStep = 2" class="text-green-700 hover:underline">
                      Edit
                    </button>
                  </div>

                  <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div>
                      <p class="text-sm text-gray-500">First Name</p>
                      <p class="font-medium">Phal</p>
                    </div>

                    <div>
                      <p class="text-sm text-gray-500">Last Name</p>
                      <p class="font-medium">Phai</p>
                    </div>

                    <div>
                      <p class="text-sm text-gray-500">Username</p>
                      <p class="font-medium">phaiecobyte</p>
                    </div>

                    <div>
                      <p class="text-sm text-gray-500">Email</p>
                      <p class="font-medium">phaiecobyte@gmail.com</p>
                    </div>

                    <div>
                      <p class="text-sm text-gray-500">Phone</p>
                      <p class="font-medium">0965799628</p>
                    </div>

                    <div>
                      <p class="text-sm text-gray-500">Password</p>
                      <p class="font-medium">••••••••••</p>
                    </div>
                  </div>
                </div>

                <!-- Terms -->
                <div class="rounded-xl border border-gray-200 p-6">
                  <label class="flex items-center gap-3">
                    <input type="checkbox" class="w-4 h-4 accent-green-700" />

                    <span> I agree to the Terms and Conditions. </span>
                  </label>
                </div>
              </div>
            }

          @if (currentStep === 1 || currentStep === 2) {
            <div class="flex flex-col sm:flex-row gap-4 mt-10">
              <button
                class="bg-green-800 hover:bg-green-900 text-white px-8 py-3 rounded-lg transition"
                (click)="next()"
              >
                បន្ទាប់
              </button>

              <button
                class="border border-gray-300 hover:bg-gray-100 px-8 py-3 rounded-lg transition"
                (click)="previous()"
              >
                ថយក្រោយ
              </button>
            </div>
          } @else {
            <div class="flex flex-col sm:flex-row gap-4 mt-10">
              <button
                class="bg-green-800 hover:bg-green-900 text-white px-8 py-3 rounded-lg transition"
                onclick=""
              >
                រក្សាទុក
              </button>

              <button
                class="border border-gray-300 hover:bg-gray-100 px-8 py-3 rounded-lg transition"
                (click)="previous()"
              >
                ថយក្រោយ
              </button>
            </div>
          }
        </div>
      </div>
    </div>
  `,
  imports: [TextFieldComponent, SelectFieldComponent, StepperComponent],
})
export class Register {
  currentStep = 1;

  steps = [
    {
      title: 'ព័ត៌មានអាជីវកម្ម',
      description: 'ឈ្មោះ ប្រភេទ និងអាសយដ្ឋាន',
    },
    {
      title: 'ម្ចាស់គណនី',
      description: 'បង្កើតអ្នកគ្រប់គ្រងប្រព័ន្ធ',
    },
    {
      title: 'បញ្ចប់',
      description: 'ពិនិត្យ និងរក្សាទុក',
    },
  ];

  businessTypes = [
    { label: 'Retail', value: 'RETAIL' },
    { label: 'Restaurant', value: 'RESTAURANT' },
    { label: 'Wholesale', value: 'WHOLESALE' },
    { label: 'Service', value: 'SERVICE' },
  ];

  provinces = [
    { label: 'ភ្នំពេញ', value: 'Phnom Penh' },
    { label: 'បន្ទាយមានជ័យ', value: 'Banteay Meanchey' },
    { label: 'បាត់ដំបង', value: 'Battambang' },
    { label: 'កំពង់ចាម', value: 'Kampong Cham' },
    { label: 'កំពង់ឆ្នាំង', value: 'Kampong Chhnang' },
    { label: 'កំពង់ស្ពឺ', value: 'Kampong Speu' },
    { label: 'កំពង់ធំ', value: 'Kampong Thom' },
    { label: 'កំពត', value: 'Kampot' },
    { label: 'កណ្តាល', value: 'Kandal' },
    { label: 'កែប', value: 'Kep' },
    { label: 'កោះកុង', value: 'Koh Kong' },
    { label: 'ក្រចេះ', value: 'Kratie' },
    { label: 'មណ្ឌលគិរី', value: 'Mondulkiri' },
    { label: 'ឧត្តរមានជ័យ', value: 'Oddar Meanchey' },
    { label: 'ប៉ៃលិន', value: 'Pailin' },
    { label: 'ព្រះសីហនុ', value: 'Preah Sihanouk' },
    { label: 'ព្រះវិហារ', value: 'Preah Vihear' },
    { label: 'ព្រៃវែង', value: 'Prey Veng' },
    { label: 'ពោធិ៍សាត់', value: 'Pursat' },
    { label: 'រតនគិរី', value: 'Ratanakiri' },
    { label: 'សៀមរាប', value: 'Siem Reap' },
    { label: 'ស្ទឹងត្រែង', value: 'Stung Treng' },
    { label: 'ស្វាយរៀង', value: 'Svay Rieng' },
    { label: 'តាកែវ', value: 'Takeo' },
    { label: 'ត្បូងឃ្មុំ', value: 'Tboung Khmum' },
  ];

  next() {
    if (this.currentStep < this.steps.length) {
      this.currentStep++;
    }
  }

  previous() {
    if (this.currentStep > 1) {
      this.currentStep--;
    }
  }
}
