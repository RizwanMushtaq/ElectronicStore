import type { Cart } from "../hooks/useGetCart";
import { getHeadersWithAuthorization } from "./sharedUtils";

interface AddItemToCartByUserIdPayload {
  productId: string;
  quantity: number;
  userId: string;
}

export const addItemToCartByUserId = async ({
  productId,
  quantity,
  userId,
}: AddItemToCartByUserIdPayload): Promise<Cart> => {
  const apiUrl = import.meta.env.VITE_API_BASE_URL + "/carts/" + userId;

  try {
    const response = await fetch(apiUrl, {
      method: "POST",
      headers: getHeadersWithAuthorization(),
      body: JSON.stringify({ productId, quantity }),
    });
    if (!response.ok) {
      throw new Error(`Error ${response.status}: ${response.statusText}`);
    }
    return response.json();
  } catch (error) {
    console.error("Error adding item to cart:", error);
    throw new Error();
  }
};
