import { useAuthContext } from "../auth/AuthContext";
import Header from "../components/Header";
import { useGetCart } from "../hooks/useGetCart";

const Cart: React.FC = () => {
  const { userId } = useAuthContext();
  console.log("User ID from AuthContext:", userId);

  const { isPending, isError, data, error } = useGetCart(userId);

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
    return (
      <>
        <Header></Header>
        <div className="container mx-auto py-8">
          <h1 className="text-3xl font-bold mb-4">Your Cart</h1>
          <p className="text-red-600">Error loading cart: {error.message}</p>
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
        </div>
      </>
    );
  }
};

export default Cart;
