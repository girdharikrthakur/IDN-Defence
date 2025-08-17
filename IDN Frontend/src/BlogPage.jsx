import { useState, useEffect } from "react";
import { Link } from "react-router-dom";

const Blog = () => {
    const [news, setNews] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const startTime = Date.now();

        fetch("http://localhost:8080/api/v1/posts")
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Failed to fetch data");
                }
                return response.json();
            })
            .then((data) => {
                setNews(data);

                const elapsed = Date.now() - startTime;
                const delay = Math.max(0, 1000 - elapsed);

                setTimeout(() => {
                    setLoading(false);
                }, delay);
            })
            .catch((error) => {
                setError(error.message);
                setLoading(false);
            });
    }, []);

    const handleReadMore = async (id) => {
        if (!id) return;
        try {
            const response = await fetch(`http://localhost:8080/api/v1/posts/${id}/increment-views`, {
                method: "PATCH",
                headers: { "Content-Type": "application/json" }
            });

            if (!response.ok) {
                throw new Error("Failed to update views");
            }

            setNews((prevNews) =>
                prevNews.map((item) =>
                    item.id === id ? { ...item, views: item.views + 1 } : item
                )
            );
        } catch (error) {
            console.error("Error updating views:", error);
        }
    };

    // Loader
if (loading) {
  return (
    <div className="fixed inset-0 z-50 grid place-items-center bg-white/70 backdrop-blur-sm">
      <div
        aria-label="Loading"
        className="h-16 w-16 rounded-full border-4 border-gray-300 border-t-transparent animate-spin"
      />
      <p className="mt-4 text-base font-semibold text-gray-700">Loading News…</p>
    </div>
  );
}


    if (error) return <p className="text-red-600 text-center mt-10">Error: {error}</p>;

    const newsItem = news.length > 0 ? news[0] : null;
    const maxViews =
        news.length > 0
            ? news.reduce((maxItem, item) =>
                item.views > maxItem.views ? item : maxItem, news[0])
            : null;

    return (
        <>
            <div className="flex flex-wrap justify-center gap-4 w-full bg-gray-200 p-4">
                {/* Latest News Section */}
                {newsItem && (
                    <div className="rounded-xl flex flex-col justify-center items-center bg-gray-100 text-red-600 h-auto md:h-[400px] w-full md:w-[45%] p-4 shadow-lg">
                        <h1 className="text-xl md:text-2xl font-bold">Latest News</h1>
                        <h1 className="text-black font-semibold text-lg md:text-3xl text-center">{newsItem.title}</h1>
                        {newsItem.imgUrl && (
                            <img src={newsItem.imgUrl} alt="image" className="w-full max-w-[300px] md:max-w-[400px] h-48 md:h-60 mt-2 rounded" />
                        )}
                        <p className="text-gray-700 text-center line-clamp-3">{newsItem.content}</p>
                        <p className="text-blue-600"><strong>Category:</strong> {newsItem.category}</p>
                        <p className="text-red-800"><strong>Author:</strong> {newsItem.author}</p>
                        <p><strong>Published At:</strong> {newsItem.publishedAt ? new Date(newsItem.publishedAt).toLocaleString() : "N/A"}</p>
                        <Link to={`/news/${newsItem.id}`} onClick={() => handleReadMore(newsItem.id)} className="text-blue-500 underline">
                            Read More
                        </Link>
                        <p><strong>Views:</strong> {newsItem.views ?? 0}</p>
                    </div>
                )}

                {/* Trending News Section */}
                {maxViews && (
                    <div className="rounded-xl flex flex-col justify-center items-center bg-gray-100 h-auto md:h-[400px] w-full md:w-[45%] p-4 shadow-lg">
                        <h1 className="font-semibold text-xl md:text-2xl p-2">Trending News</h1>
                        {maxViews.imgUrl && (
                            <img src={maxViews.imgUrl} alt="image" className="w-full max-w-[300px] md:max-w-[400px] h-48 md:h-60 mt-2 rounded" />
                        )}
                        <h1 className="text-black font-semibold text-lg md:text-3xl text-center">{maxViews.title}</h1>
                        <p className="text-gray-700 text-center line-clamp-3">{maxViews.content}</p>
                        <p className="text-blue-600"><strong>Category:</strong> {maxViews.category}</p>
                        <p className="text-red-800"><strong>Author:</strong> {maxViews.author}</p>
                        <p><strong>Published At:</strong> {maxViews.publishedAt ? new Date(maxViews.publishedAt).toLocaleString() : "N/A"}</p>
                        <p><strong>Views:</strong> {maxViews.views ?? 0}</p>
                    </div>
                )}
            </div>

            <div className="bg-gray-200 min-h-screen p-6">
                <h1 className="text-black font-bold text-3xl text-center mb-6">News Articles</h1>
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    {news.map((item) => (
                        <div key={item.id} className="bg-gray-100 p-4 shadow-md rounded-lg relative h-[600px] flex flex-col">
                            <h2 className="text-xl font-semibold line-clamp-2">{item.title}</h2>
                            <img
                                src={item.imgUrl}
                                alt="news image"
                                className="w-full h-60 mt-2 rounded object-cover"
                            />
                            <p className="text-gray-700 mt-2 line-clamp-7">{item.content}</p>
                            <button
                                className="text-blue-500 underline absolute bottom-20 left-4"
                                onClick={() => handleReadMore(item.id)}>Read More</button>
                            <div className="absolute bottom-4 left-4 right-4 flex flex-row gap-2 text-sm text-gray-600">
                                <p className="text-blue-600"><strong>Category:</strong> {item.category}</p>
                                <p className="text-red-800"><strong>Author:</strong> {item.author}</p>
                                <p><strong>Published At:</strong> {new Date(item.publishedAt).toLocaleString()}</p>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </>
    );
};

export default Blog;
