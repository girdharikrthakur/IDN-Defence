import data from "../assets/data.json";
import NewsCard from "../components/NewsCard";
import { useEffect, useState } from "react";

function Home() {
  const [article, setArticle] = useState(null);
  const [mostViewed, setMostViewed] = useState(null);

  useEffect(() => {
    fetch("https://api.example.com/most-viewed")
      .then((res) => res.json())
      .then((data) => setArticle(data)) // single object
      .catch((err) => console.error(err));

    fetch("https://api.example.com/most-viewed")
      .then((res) => res.json())
      .then((data) => setMostViewed(data))
      .catch((err) => console.error(err));
  }, []);

  return (
    <>
      <div className="flex w-ful bg-amber-50">
        <div className="flex justify-center p-6 mix-h-100">
          {article && (
            <div className="w-80 border p-4 rounded shadow bg-gray-200">
              <img
                src={article.imageUrl}
                alt={article.title}
                className="w-full h-40 object-cover rounded"
              />

              <h2 className="font-bold mt-2">{article.title}</h2>
              <p className="text-sm text-gray-600">{article.heading}</p>

              <div className="flex justify-between mt-2 text-sm">
                <span>❤️ {article.likes}</span>
                <span>{article.author}</span>
              </div>

              <p className="text-xs text-gray-400 mt-1">
                {new Date(article.createdAt).toLocaleDateString()}
              </p>
            </div>
          )}
        </div>

        <div className="flex justify-center p-6 min-h-100">
          {mostViewed && (
            <div className="w-80 border p-4 rounded shadow">
              <img
                src={mostViewed.imageUrl}
                alt={mostViewed.title}
                className="w-full h-40 object-cover rounded"
              />

              <h2 className="font-bold mt-2">{mostViewed.title}</h2>
              <p className="text-sm text-gray-600">{mostViewed.heading}</p>

              <div className="flex justify-between mt-2 text-sm">
                <span>❤️ {mostViewed.likes}</span>
                <span>{mostViewed.author}</span>
              </div>

              <p className="text-xs text-gray-400 mt-1">
                {new Date(mostViewed.createdAt).toLocaleDateString()}
              </p>
            </div>
          )}
        </div>
      </div>

      <div className="mt-8">
        <div className=" flex flex-row gap-4 flex-wrap justify-center  min-w-100">
          {data.slice(0, 6).map((item) => (
            <NewsCard
              key={item.id}
              title={item.title}
              heading={item.heading}
              imageUrl={item.imageUrl}
              views={item.views}
              author={item.author}
              createdAt={item.createdAt}
            />
          ))}
        </div>
      </div>
    </>
  );
}
export default Home;
