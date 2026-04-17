import AdminUser from "../Components/AdminUser.jsx";
import PostCard from "../Components/PostCard.jsx";

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
