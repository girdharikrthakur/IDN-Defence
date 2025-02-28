import { useState, useEffect } from "react";

function Blog() {
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
                setNews(data); // Store the array of news articles
                setLoading(false);
            })
            .catch((error) => {
                setError(error.message);
                setLoading(false);
            });
    }, []);

    if (loading) return <p>Loading...</p>;
    if (error) return <p>Error: {error}</p>;

    return (
        <div className="bg-gray-100 min-h-screen p-6">
            <h1 className="text-black font-bold text-3xl text-center mb-6">News Articles</h1>
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {news.map((item) => (
                    <div key={item.id} className="bg-white p-4 shadow-md rounded-lg">
                        <h2 className="text-xl font-semibold">{item.title}</h2>
                        <p className="text-gray-700">{item.content}</p>
                        <p className="text-blue-600"><strong>Category:</strong> {item.category}</p>
                        <p className="text-red-800"><strong>Author:</strong> {item.author}</p>
                        <p><strong>Published At:</strong> {new Date(item.publishedAt).toLocaleString()}</p>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default Blog;
