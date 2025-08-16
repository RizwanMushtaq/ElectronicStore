import { useQuery } from "@tanstack/react-query";
import type { Order } from "../utils/orderUtils";
import { getHeadersWithAuthorization } from "../utils/sharedUtils";

async function fetchCart(userId: string): Promise<Order[]> {
  const apiUrl = import.meta.env.VITE_API_BASE_URL + "/orders/" + userId;

  const response = await fetch(apiUrl, {
    method: "GET",
    headers: getHeadersWithAuthorization(),
  });

  if (!response.ok) {
    throw new Error(`Error ${response.status}: ${response.statusText}`);
  }
  return response.json();
}

export function useGetOrders(userId: string) {
  return useQuery<Order[], Error>({
    queryKey: ["orders"],
    queryFn: () => fetchCart(userId),
    staleTime: 1000 * 60 * 5,
    retry: 2,
    refetchOnWindowFocus: false,
    refetchOnMount: "always",
  });
}
