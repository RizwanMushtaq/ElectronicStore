import Header from "../components/Header";

const Cart: React.FC = () => {
  return (
    <>
      <Header></Header>
      <div className="container mx-auto py-8">
        <h1 className="text-3xl font-bold mb-4">Your Cart</h1>
        <p className="text-gray-600">Your cart is currently empty.</p>
      </div>
    </>
  );
};

export default Cart;
