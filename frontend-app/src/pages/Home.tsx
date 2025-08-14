import React from "react";
import { useGetProducts } from "./utils/hooks/useGetProducts";

const Home: React.FC = () => {
  const { data, isLoading, error } = useGetProducts();

  if (isLoading) return "Loading";

  if (error) return "An error has occurred: " + error.message;

  return (
    <>
      <h1 className="text-2xl font-mono underline"> Electronic Store </h1>
      {data &&
        data.content.map((item) => <div key={item.id}>{item.title}</div>)}
    </>
  );
};

export default Home;
