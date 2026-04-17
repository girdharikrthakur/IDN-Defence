import AdminUser from "../components/AdminUser.jsx";
import PostCard from "../components/PostCard.jsx";

export default function Dashboard() {
  return (
    <>
      <div className="min-h-screen">
        <PostCard />
        <AdminUser />
      </div>
    </>
  );
}
