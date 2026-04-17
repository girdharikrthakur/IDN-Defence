import data from "../assets/data.json";
import NewsCard from "../Components/NewsCard.jsx";
import { useNavigate } from "react-router-dom";

function Home() {
  const navigate = useNavigate();
  return (
    <>
      <div
        onClick={() => navigate(`/article/${data[0].id}`)}
        className="flex flex-wrap gap-4 mt-20 px-4"
      >
        {/* LEFT BIG CARD */}
        <div className="w-full md:w-[48%] h-[400px] bg-gray-100 shadow rounded overflow-hidden">
          <img src={data[0].imageUrl} className="w-full h-60 object-cover" />
          <div className="p-4">
            <h2 className="text-xl font-bold">{data[0].title}</h2>
            <h1 className="text-gray-600 mt-2">{data[0].heading}</h1>
          </div>
        </div>

        {/* RIGHT SECTION (4 CARDS) */}
        <div className="w-full md:w-[48%] h-[400px] bg-gray-100 shadow rounded overflow-hidden">
          <img src={data[2].imageUrl} className="w-full h-60 object-cover" />
          <div className="p-4">
            <h2 className="text-xl font-bold">{data[0].title}</h2>
            <h1 className="text-gray-600 mt-2">{data[0].heading}</h1>
          </div>
        </div>
      </div>

      {/* Lower Card */}
      <div className="mt-8 px-4">
        <div className="flex flex-wrap justify-center min-w-[400px]">
          {data.slice(0, 6).map((item) => (
            <NewsCard key={item.id} {...item} />
          ))}
        </div>
      </div>
    </>
  );
}

export default Home;
