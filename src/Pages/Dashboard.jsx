import AdminUser from "../components/AdminUser";
import PostCard from "../components/PostCard";

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
