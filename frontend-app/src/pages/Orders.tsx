import Header from "../components/Header";

const Orders: React.FC = () => {
  return (
    <>
      <Header></Header>
      <div className="container mx-auto py-8">
        <h1 className="text-3xl font-bold mb-4">Your Orders</h1>
        <p className="text-gray-600">You have no orders yet.</p>
      </div>
    </>
  );
};

export default Orders;
