import { useQuery } from "@tanstack/react-query";
import type { Product } from "./useGetProducts";
import type { User } from "../auth/login";
import { getAuthToken } from "../utils/localStorage";

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
  const token = getAuthToken() || "dummyToken";
  const apiUrl = import.meta.env.VITE_API_BASE_URL + "/carts/" + userId;

  const response = await fetch(apiUrl, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
  });

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
    retry: 0,
    refetchOnWindowFocus: false,
    refetchOnMount: "always",
  });
}
