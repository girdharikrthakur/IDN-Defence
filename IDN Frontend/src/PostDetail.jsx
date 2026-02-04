import { useState, useEffect } from "react";

const PostDetail = ({ id, onClose, onViewIncrement }) => {
  const [post, setPost] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!id) return;

    const fetchPost = async () => {
      try {
        const res = await fetch(`http://localhost:8080/public/api/v1/posts/${id}`);
        if (!res.ok) throw new Error("Failed to fetch post");
        const data = await res.json();
        setPost(data);
        setLoading(false);

        // Increment views on modal open
        if (typeof onViewIncrement === "function") {
          await fetch(`http://localhost:8080/public/api/v1/posts/${id}/increment-views`, {
            method: "PATCH",
            headers: { "Content-Type": "application/json" },
          });
          onViewIncrement(id); // update blog state
        }
      } catch (err) {
        setError(err.message);
        setLoading(false);
      }
    };

    fetchPost();
  }, [id, onViewIncrement]);

  if (loading) return <p className="text-center p-4">Loading post...</p>;
  if (error) return <p className="text-red-600 text-center p-4">{error}</p>;
  if (!post) return <p className="text-center p-4">Post not found</p>;

  return (
    <div className="fixed inset-0 bg-black/50 flex justify-center items-start p-6 overflow-auto z-50">
      <div className="bg-white rounded-lg shadow-lg max-w-3xl w-full p-6 relative">
        <button
          onClick={onClose}
          className="absolute top-2 right-2 text-red-600 font-bold text-xl"
        >
          ×
        </button>
        <h2 className="text-2xl font-bold mb-2">{post.title}</h2>
        {post.imgUrl && (
          <img
            src={post.imgUrl}
            alt={post.title}
            className="w-full h-64 object-cover rounded mb-4"
          />
        )}
        <p className="text-gray-700 mb-2">{post.content}</p>
        <div className="text-sm text-gray-600 space-y-1">
          <p><strong>Category:</strong> {post.categoryName ?? "N/A"}</p>
          <p><strong>Author:</strong> {post.authorName ?? "Unknown"}</p>
          <p><strong>Published:</strong> {post.publishedAt ? new Date(post.publishedAt).toLocaleString() : "N/A"}</p>
          <p><strong>Views:</strong> {post.views ?? 0}</p>
        </div>
      </div>
    </div>
  );
};

export default PostDetail;
