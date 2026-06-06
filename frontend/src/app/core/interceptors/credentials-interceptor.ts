import { HttpInterceptorFn } from '@angular/common/http';

export const credentialsInterceptor: HttpInterceptorFn = (req, next) => {
  // ក្លូន (Clone) Request ចាស់ រួចបន្ថែម withCredentials ដើម្បីអនុញ្ញាតការផ្ញើ Cookies
  const clonedRequest = req.clone({
    withCredentials: true
  });

  // បញ្ជូន Request ដែលបានកែប្រែរួចទៅមុខបន្ត
  return next(clonedRequest);
};
