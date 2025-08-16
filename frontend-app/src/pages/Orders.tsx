import { useAuthContext } from "../auth/AuthContext";
import Header from "../components/Header";
import { useGetOrders } from "../hooks/useGetOrders";

const Orders: React.FC = () => {
  const { userId } = useAuthContext();
  const { isPending, isError, data, error } = useGetOrders(userId);

  if (isPending) {
    return (
      <>
        <Header></Header>
        <div className="container mx-auto py-8">
          <h1 className="text-3xl font-bold mb-4">Your Orders</h1>
          <p className="text-gray-600">Loading your orders...</p>
        </div>
      </>
    );
  }

  if (isError) {
    return (
      <>
        <Header></Header>
        <div className="container mx-auto py-8">
          <h1 className="text-3xl font-bold mb-4">Your Orders</h1>
          <p className="text-red-600">Error loading orders: {error.message}</p>
        </div>
      </>
    );
  }

  if (data.length === 0) {
    return (
      <>
        <Header></Header>
        <div className="container mx-auto py-8">
          <h1 className="text-3xl font-bold mb-4">Your Orders</h1>
          <p className="text-gray-600">You have no orders yet.</p>
        </div>
      </>
    );
  }

  if (data.length > 0) {
    return (
      <>
        <Header></Header>
        <div className="container mx-auto py-8">
          <h1 className="text-3xl font-bold mb-4">Your Orders</h1>
          <ul className="space-y-4">
            {data.map((order) => (
              <li key={order.id} className="border p-4 rounded">
                <h2 className="text-xl font-semibold">Order #{order.id}</h2>
                {order.orderedDate && (
                  <p>
                    Date: {new Date(order.orderedDate).toLocaleDateString()}
                  </p>
                )}
                <p>Total: {order.orderAmount.toFixed(2)}</p>
              </li>
            ))}
          </ul>
        </div>
      </>
    );
  }
};

export default Orders;
