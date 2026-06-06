/**
 *  Author: PHAL PHAI
 *  Position: JAVA Developer
 *  Date: 06 June 2026
 * 
 */

/** 
 * ----------------------------------------------------
 *                  COMMON
 * ----------------------------------------------------
 * Description: I called them common because in backend
 * I place them in module common.
 * 
*/

export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
}

export interface PageResponse<T> {
  content: T[];

  totalElements: number;
  totalPages: number;

  size: number;
  number: number;

  first: boolean;
  last: boolean;

  numberOfElements: number;
}


/**
 * ---------------------------------------------------
 *                      CORE
 * ---------------------------------------------------
 *  Description: I called them core because in backend
 *  I place them in module core.
 * 
 */

export const BUSINESS_TYPES = [
  {
    code: 'RETAIL',
    name: 'Retail'
  },
  {
    code: 'RESTAURANT',
    name: 'Restaurant'
  }
] as const;

export interface Tenant {
  id: string;
  code: string;
  businessName: string;
  phone: string;
  email: string;
  subscriptionEndDate: string;
  address: string;
  status: string;
  businessTypeCode: string;
  createdAt: string;
  createdBy: string | null;
  updatedAt: string | null;
  updatedBy: string | null;
}


export interface TenantForm {

  code: string;
  businessTypeCode: string;
  businessName: string;
  phone: string;
  email: string;
  address: string;
  subscriptionEndDate: string;

}
