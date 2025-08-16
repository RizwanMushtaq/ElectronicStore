import { useAuthContext } from "../auth/AuthContext";
import Header from "../components/Header";
import { useGetCart } from "../hooks/useGetCart";
import { useEffect, useState } from "react";
import { placeOrder } from "../utils/orderUtils";
import { useQueryClient } from "@tanstack/react-query";

const Cart: React.FC = () => {
  const { userId } = useAuthContext();
  const queryClient = useQueryClient();

  const { isPending, isError, data, error } = useGetCart(userId);
  const [ordering, setOrdering] = useState(false);
  const [orderError, setOrderError] = useState<string | null>(null);
  const [orderSuccess, setOrderSuccess] = useState(false);
  const [reloadCart, setReloadCart] = useState(false);

  useEffect(() => {
    queryClient.invalidateQueries({ queryKey: ["cart"] });
  }, [reloadCart, queryClient]);

  const handleOrderNow = async () => {
    setOrdering(true);
    setOrderError(null);
    setOrderSuccess(false);
    console.log("Placing order for user:", userId);

    try {
      await placeOrder({
        userId,
        cartId: data!.id,
        billingName: "dummy name",
        billingPhone: "dummy phone",
        billingAddress: "dummy address",
      });
      setOrdering(false);
      setOrderSuccess(true);
      if (reloadCart) {
        setReloadCart(false);
      } else {
        setReloadCart(true);
      }
      alert("Order placed successfully!");
    } catch (error) {
      console.error("Failed to place order:", error);
      setOrdering(false);
      setOrderError("Failed to place order. Please try again.");

      alert("Failed to place order. Please try again.");
    }
  };

  if (isPending) {
    return (
      <>
        <Header></Header>
        <div className="container mx-auto py-8">
          <h1 className="text-3xl font-bold mb-4">Your Cart</h1>
          <p className="text-gray-600">Loading your cart...</p>
        </div>
      </>
    );
  }

  if (isError) {
    console.error("Error fetching cart:", error);
    return (
      <>
        <Header></Header>
        <div className="container mx-auto py-8">
          <h1 className="text-3xl font-bold mb-4">Your Cart</h1>
          <p className="text-red-600">
            {error.message.includes("404")
              ? "Cart not found."
              : "An error occurred while loading your cart."}
          </p>
        </div>
      </>
    );
  }

  if (!data || !data.cartItems || data.cartItems.length === 0) {
    return (
      <>
        <Header></Header>
        <div className="container mx-auto py-8">
          <h1 className="text-3xl font-bold mb-4">Your Cart</h1>
          <p className="text-gray-600">Your cart is currently empty.</p>
        </div>
      </>
    );
  }

  if (data && data.cartItems.length > 0) {
    return (
      <>
        <Header></Header>
        <div className="container mx-auto py-8">
          <h1 className="text-3xl font-bold mb-4">Your Cart</h1>
          <ul>
            {data.cartItems.map((item) => (
              <li key={item.id} className="mb-4">
                <div className="flex justify-between">
                  <span>{item.product.title}</span>
                  <span>Quantity: {item.quantity}</span>
                  <span>Total Price: ${item.totalPrice.toFixed(2)}</span>
                </div>
              </li>
            ))}
          </ul>
          <div className="mt-6">
            <button
              className="bg-blue-600 text-white px-6 py-2 rounded hover:bg-blue-700 disabled:opacity-50"
              onClick={handleOrderNow}
              disabled={ordering}
            >
              {ordering ? "Ordering..." : "Order Now"}
            </button>
            {orderError && <p className="text-red-600 mt-2">{orderError}</p>}
            {orderSuccess && (
              <p className="text-green-600 mt-2">Order placed successfully!</p>
            )}
          </div>
        </div>
      </>
    );
  }
};

export default Cart;
