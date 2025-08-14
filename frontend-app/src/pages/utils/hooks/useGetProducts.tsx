import { useQuery } from "@tanstack/react-query";

export interface Product {
  id: string;
  title: string;
  description: string;
  price: number;
  discountedPrice: number;
  quantity: number;
  addedDate: Date;
  live: boolean;
  stocked: boolean;
  productImageName: string;
}

export interface ProductPageResponse {
  content: Product[];
  pageNumber: number;
}

async function fetchProducts(): Promise<ProductPageResponse> {
  const apiUrl = import.meta.env.VITE_API_BASE_URL + "/products";
  const response = await fetch(apiUrl);
  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.statusText}`);
  }
  return response.json();
}

export function useGetProducts() {
  return useQuery<ProductPageResponse, Error>({
    queryKey: ["products"],
    queryFn: () => fetchProducts(),
    staleTime: 1000 * 60 * 5,
    retry: 2,
  });
}
