import { useState } from "react";

export default function PostNews() {
  const [formData, setFormData] = useState({
    title: "",
    image: null, // file object
    content: "",
    category: "",
    author: ""
  });
  const [response, setResponse] = useState(null);
  const [error, setError] = useState(null);

  const handleChange = (e) => {
    const { name, value, files } = e.target;
    if (name === "file") {
      setFormData({ ...formData, image: files[0] });
    } else {
      setFormData({ ...formData, [name]: value });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setResponse(null);

    try {
      const data = new FormData();
      data.append("title", formData.title);
      data.append("content", formData.content);
      data.append("category", formData.category);
      data.append("author", formData.author);
      if (formData.image) {
        data.append("file", formData.image);
      }

      const res = await fetch("http://localhost:8080/public/api/v1/posts", {
        method: "POST",
        body: data, // don't set Content-Type manually, browser will handle it
      });

      const result = await res.json();
      if (!res.ok) {
        throw new Error(result.message || "Something went wrong");
      }
      setResponse(result);
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <>
      <h1 className="text-center p-4 bg-gray-300">Just to test API</h1>

      <div className="max-w-md mx-auto p-4 border rounded-lg shadow">
        <h2 className="text-xl font-bold mb-4">Send POST Request</h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <input
            type="text"
            name="title"
            value={formData.title}
            onChange={handleChange}
            placeholder="Enter title"
            className="w-full p-2 border rounded"
            required
          />

          <input
            type="file"
            name="file"
            onChange={handleChange}
            accept="image/*"
            className="w-full p-2 border rounded bg-gray-100"
          />


          <textarea
            name="content"
            placeholder="Write your article here..."
            value={formData.content}
            onChange={handleChange}
            className="w-full p-2 border rounded"
            required
          ></textarea>

          <input
            type="text"
            name="category"
            value={formData.category}
            onChange={handleChange}
            placeholder="Enter category"
            className="w-full p-2 border rounded"
            required
          />

          <input
            type="text"
            name="author"
            value={formData.author}
            onChange={handleChange}
            placeholder="Enter author"
            className="w-full p-2 border rounded"
            required
          />

          <button
            type="submit"
            className="w-full bg-blue-500 text-white p-2 rounded hover:bg-blue-600"
          >
            Submit
          </button>
        </form>

        {response && (
          <p className="mt-4 text-green-500">
            Success: {JSON.stringify(response)}
          </p>
        )}
        {error && <p className="mt-4 text-red-500">Error: {error}</p>}
      </div>
    </>
  );
}
