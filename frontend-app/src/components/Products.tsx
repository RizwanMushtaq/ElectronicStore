import { useGetProducts, type Product } from "../hooks/useGetProducts";

const Products: React.FC = () => {
  const { data, isLoading, error } = useGetProducts();

  if (isLoading) return "Loading...";

  if (error) return "An error has occurred: " + error.message;

  return (
    <div className="container mx-auto p-6">
      <h2 className="text-2xl font-bold mb-4">Products</h2>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {data &&
          data.content.map((item: Product) => (
            <div key={item.id} className="border p-4 rounded-lg shadow">
              <h3 className="text-xl font-semibold">{item.title}</h3>
              <p className="text-gray-600">{item.description}</p>
            </div>
          ))}
      </div>
    </div>
  );
};

export default Products;
