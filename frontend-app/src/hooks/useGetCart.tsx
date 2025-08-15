import { useQuery } from "@tanstack/react-query";
import type { Product } from "./useGetProducts";
import type { User } from "../auth/login";

export interface CartItem {
  id: number;
  quantity: number;
  totalPrice: number;
  product: Product;
}

export interface Cart {
  id: string;
  createdAt: Date;
  cartItems: CartItem[];
  user: User;
}

async function fetchCart(userId: string): Promise<Cart> {
  const apiUrl = import.meta.env.VITE_API_BASE_URL + "/cart/" + userId;
  const response = await fetch(apiUrl);

  const data = await response.json();
  console.log("Data - Cart fetched:", data);

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.statusText}`);
  }
  return response.json();
}

export function useGetCart(userId: string) {
  return useQuery<Cart, Error>({
    queryKey: ["cart"],
    queryFn: () => fetchCart(userId),
    staleTime: 1000 * 60 * 5,
    retry: 2,
  });
}
