export default function NewsCard({
  title,
  heading,
  imageUrl,
  views,
  author,
  createdAt,
}) {
  return (
    <div className="m-4 p-4 rounded shadow-md  min-w-100 max-w-100 min-h-75">
      <img
        src={imageUrl}
        alt={title}
        className="w-full h-40 object-cover rounded"
      />

      <h2 className="text-lg font-bold mt-2">{title}</h2>
      <p className="text-sm text-gray-600">{heading}</p>

      <div className="flex justify-between items-center mt-2 text-sm">
        <span>👀 {views}</span>
        <span>{author}</span>
      </div>

      <p className="text-xs text-gray-400 mt-1">
        {new Date(createdAt).toLocaleDateString()}
      </p>
    </div>
  );
}
