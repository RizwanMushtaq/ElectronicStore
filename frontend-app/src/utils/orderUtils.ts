import type { User } from "../auth/login";
import { getHeadersWithAuthorization } from "./sharedUtils";

export interface Order {
  id: string;
  orderStatus: string;
  paymentStatus: string;
  orderAmount: number;
  billingName: string;
  billingPhone: string;
  billingAddress: string;
  orderDate: string;
  deliveredDate: string | null;
  user: User;
}

interface PlaceOrderPayload {
  userId: string;
  cartId: string;
  billingName: string;
  billingPhone: string;
  billingAddress: string;
}

export const placeOrder = async (
  placeOrderPayload: PlaceOrderPayload
): Promise<Order> => {
  const apiUrl = import.meta.env.VITE_API_BASE_URL + "/orders";

  try {
    const response = await fetch(apiUrl, {
      method: "POST",
      headers: getHeadersWithAuthorization(),
      body: JSON.stringify(placeOrderPayload),
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
