// import { inject } from '@angular/core';
// import { CanActivateFn, Router } from '@angular/router';
// import { AuthService } from '../api/auth-service';

// export const authGuard: CanActivateFn = (route, state) => {
//   // ប្រើប្រាស់មុខងារ inject() ដើម្បីទាញយក Service មកប្រើ
//   const authService = inject(AuthService);
//   const router = inject(Router);

//   // ឆែកមើលថាតើអ្នកប្រើប្រាស់បាន Login រួចឬនៅ (តាមរយៈ Signal ក្នុង AuthService)
//   if (authService.isAuthenticated()) {
//     return true; // អនុញ្ញាតឲ្យចូលទៅកាន់ទំព័រនោះបាន
//   }

//   // ប្រសិនបើមិនទាន់ Login ទេ បញ្ជូនគាត់ត្រឡប់ទៅកាន់ទំព័រ Login វិញភ្លាមៗ
//   return router.createUrlTree(['/auth/login']);
// };
