import { useState } from "react";

export default function PostNews() {
  const [formData, setFormData] = useState({
    title: "",
    content: "",
    category: "",
    author: ""
  });
  const [response, setResponse] = useState(null);
  const [error, setError] = useState(null);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);
    setResponse(null);
    
    try {
      const res = await fetch("http://localhost:8080/api/news", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
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
    <div className="max-w-md mx-auto p-4 border rounded-lg shadow-md">
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
          type="text"
          name="content"
          value={formData.content}
          onChange={handleChange}
          placeholder="Enter content"
          className="w-full p-2 border rounded"
          required
        />
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
        <button type="submit" className="w-full bg-blue-500 text-white p-2 rounded hover:bg-blue-600">
          Submit
        </button>
      </form>
      {response && <p className="mt-4 text-green-500">Success: {JSON.stringify(response)}</p>}
      {error && <p className="mt-4 text-red-500">Error: {error}</p>}
    </div>
  );
}