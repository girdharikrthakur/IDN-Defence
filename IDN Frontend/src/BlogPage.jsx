import { useState, useEffect } from "react";
import { Link } from "react-router-dom";

const Blog = () => {
    const [news, setNews] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetch("http://localhost:8080/api/news")
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Failed to fetch data");
                }
                return response.json();
            })
            .then((data) => {
                setNews(data);
                setLoading(false);
            })
            .catch((error) => {
                setError(error.message);
                setLoading(false);
            });
    }, []);

    const handleReadMore = async (id) => {
        if (!id) return; // Prevent errors if id is undefined
        try {
            const response = await fetch(`http://localhost:8080/api/news/${id}/increment-views`, {
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

    if (loading) return <p>Loading...</p>;
    if (error) return <p>Error: {error}</p>;

    const newsItem = news.length > 0 ? news[0] : null; // Ensure there's at least one news item
    const maxViews = news.length > 0 ? news.reduce((maxItem, item) => (item.views > maxItem.views ? item : maxItem), news[0]) : null;

    return (
        <>
            <div className="flex gap-[20px] flex-row flex-wrap w-full justify-center items-center bg-gray-700 p-4">
                {/* Latest News Section */}
                {newsItem && (
                    <div className="rounded-xl flex justify-center items-center flex-col bg-gray-100 text-red-600 h-[400px] max-w-[50%] min-w-[400px] p-4">
                        <h1>Latest News</h1>
                        <h1 className="text-black font-semibold text-3xl">{newsItem.title}</h1>
                        {newsItem.imgURL && (
                            <img src={newsItem.imgURL} alt="image" className="w-96 h-60 mt-2 rounded" />
                        )}
                        <p className="text-gray-700">{newsItem.content}</p>
                        <p className="text-blue-600"><strong>Category:</strong> {newsItem.category}</p>
                        <p className="text-red-800"><strong>Author:</strong> {newsItem.author}</p>
                        <p><strong>Published At:</strong> {newsItem.publishedAt ? new Date(newsItem.publishedAt).toLocaleString() : "N/A"}</p>
                        <Link to={`/news/${newsItem.id}`} onClick={() => handleReadMore(newsItem.id)}>
                            Read More
                        </Link>
                        <p><strong>Views:</strong> {newsItem.views ?? 0}</p>

                    </div>
                )}

                {/* Trending News Section */}
                {maxViews && (
                    <div className="rounded-xl flex justify-center items-center flex-col bg-gray-100 h-[400px] max-w-[50%] min-w-[400px] p-4">
                        <h1 className="font-semibold text-2xl p-4">Trending News</h1>
                        {maxViews.imgURL && (
                            <img src={maxViews.imgURL} alt="image" className="w-96 h-60 mt-2 rounded" />
                        )}
                        <h1 className="text-black font-semibold text-3xl">{maxViews.title}</h1>
                        <p className="text-gray-700">{maxViews.content}</p>
                        <p className="text-blue-600"><strong>Category:</strong> {maxViews.category}</p>
                        <p className="text-red-800"><strong>Author:</strong> {maxViews.author}</p>
                        <p><strong>Published At:</strong> {maxViews.publishedAt ? new Date(maxViews.publishedAt).toLocaleString() : "N/A"}</p>
                        <p><strong>Views:</strong> {maxViews.views ?? 0}</p>
                    </div>
                )}
            </div>

            <div className="bg-gray-700 min-h-screen p-6">
                <h1 className="text-white font-bold text-3xl text-center mb-6">News Articles</h1>
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    {news.map((item) => (
                        <div key={item.id} className="bg-gray-100 p-4 shadow-md rounded-lg relative">
                            <h2 className="text-xl font-semibold">{item.title}</h2>
                            <img src={item.imgURL} alt="image" className="w-96 h-60 mt-2 rounded" />
                            <p className="text-gray-700 mb-16">{item.content}</p> {/* Added bottom margin for spacing */}

                            {/* Fixed info at the bottom */}
                            <div className="absolute bottom-4 left-4 right-4 flex justify-between text-sm text-gray-600">
                                <p className="text-blue-600"><strong>Category:</strong> {item.category}</p>
                                <p className="text-red-800"><strong>Author:</strong> {item.author}</p>
                                <p><strong>Published At:</strong> {new Date(item.publishedAt).toLocaleString()}</p>
                            </div>
                        </div>
                    ))}
                </div>


            </div >
        </>
    );
};

export default Blog;
