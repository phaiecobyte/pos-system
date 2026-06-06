export interface LoginRes {
  accessToken: string;
  refreshToken?: string;
}

export interface LoginReq {
  username:string;
  password:string;
}

export interface TableColumn {
  key: string;            // ឈ្មោះ Field ក្នុង JSON (ឧ. 'businessName')
  label: string;          // ឈ្មោះត្រូវបង្ហាញលើក្បាលតារាង (ឧ. 'ឈ្មោះអាជីវកម្ម')
  type?: 'text' | 'date' | 'badge' | 'custom'; // ប្រភេទនៃការបង្ហាញទិន្នន័យ
}


// ទម្រង់រួមសម្រាប់រាល់ API Response ទាំងអស់
export interface ApiResponse<T> {
  timestamp: string;
  status: number;
  success: boolean;
  message: string;
  data: T;
}

// ទម្រង់ស្តង់ដារសម្រាប់ទិន្នន័យដែលមាន Pagination (Spring Boot)
export interface PaginatedData<T> {
  content: T[];
  empty: boolean;
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  pageable: any;
  size: number;
  sort: any;
  totalElements: number;
  totalPages: number;
}

export interface MenuItem {
  label: string;
  icon: string;
  route: string;
}
