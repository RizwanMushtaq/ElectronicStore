import { useGetProducts, type Product } from "../hooks/useGetProducts";
import { useState } from "react";
import { addItemToCartByUserId } from "../utils/cartUtils";
import { useAuthContext } from "../auth/AuthContext";

const Products: React.FC = () => {
  const { userId } = useAuthContext();
  const { data, isLoading, error } = useGetProducts();
  const [addingToCart, setAddingToCart] = useState<string | null>(null);

  const handleAddToCart = async (product: Product) => {
    setAddingToCart(product.id);
    try {
      await addItemToCartByUserId({
        productId: product.id,
        quantity: 1,
        userId,
      });
      setAddingToCart(null);
      alert(`Added ${product.title} to cart!`);
    } catch (error) {
      console.error("Failed to add item to cart:", error);
      setAddingToCart(null);
      alert("Failed to add item to cart. Please try again.");
    }
  };

  if (isLoading) return "Loading...";

  if (error) return "An error has occurred: " + error.message;

  return (
    <div className="container mx-auto p-6">
      <h2 className="text-2xl font-bold mb-4">Products</h2>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {data &&
          data.content.map((item: Product) => (
            <div
              key={item.id}
              className="border p-4 rounded-lg shadow flex flex-col"
            >
              <h3 className="text-xl font-semibold mb-2">{item.title}</h3>
              <p className="text-gray-600 mb-2">{item.description}</p>
              <div className="mt-auto flex items-center justify-between">
                <span className="font-bold text-lg text-green-700">
                  ${item.discountedPrice.toFixed(2)}
                </span>
                <button
                  className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700 disabled:opacity-50"
                  onClick={() => handleAddToCart(item)}
                  disabled={addingToCart === item.id}
                >
                  {addingToCart === item.id ? "Adding..." : "Add to Cart"}
                </button>
              </div>
            </div>
          ))}
      </div>
    </div>
  );
};

export default Products;
